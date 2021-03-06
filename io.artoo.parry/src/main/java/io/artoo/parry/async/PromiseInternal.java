/*
 * Copyright (c) 2011-2019 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package io.artoo.parry.async;


/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public interface PromiseInternal<T> extends Promise<T>, FutureListener<T>, io.vertx.core.impl.future.FutureInternal<T> {

  /**
   * @return the context associated with this promise or {@code null} when there is none
   */
  ContextInternal context();

}
