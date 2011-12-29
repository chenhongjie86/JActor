/*
 * Copyright 2011 Bill La Forge
 *
 * This file is part of AgileWiki and is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (LGPL) as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * or navigate to the following url http://www.gnu.org/licenses/lgpl-2.1.txt
 *
 * Note however that only Scala, Java and JavaScript files are being covered by LGPL.
 * All other files are covered by the Common Public License (CPL).
 * A copy of this license is also included and can be
 * found as well at http://www.opensource.org/licenses/cpl1.0.txt
 */
package org.agilewiki.jactor.stateMachine;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.ResponseProcessor;

/**
 * Adds a call to send to a _SMBuilder.
 */
abstract public class _Send extends _Operation {
    /**
     * Perform the operation.
     *
     * @param stateMachine   The state machine driving the operation.
     * @param rp The response processor.
     * @throws Exception Any uncaught exceptions raised while performing the operation.
     */
    @Override
    final public void call(final StateMachine stateMachine, final ResponseProcessor rp) throws Exception {
        Actor a = getTargetActor();
        Object r = getRequest();
        stateMachine.send(a, r, new ResponseProcessor() {
            @Override
            final public void process(Object response) throws Exception {
                String rn = getResultName();
                if (rn != null) stateMachine.results.put(rn, response);
                rp.process(null);
            }
        });
    }

    abstract public Actor getTargetActor();

    abstract public Object getRequest();

    abstract public String getResultName();
}