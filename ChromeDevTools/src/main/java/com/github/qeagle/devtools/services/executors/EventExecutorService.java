package com.github.qeagle.devtools.services.executors;

/*-
 * #%L
 * cdt-java-client
 * %%
 * Copyright (C) 2018 TL
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * Event executor service.
 *
 * @author TestLeaf
 */
public interface EventExecutorService {
  /**
   * Executes a runnable in separate thread.
   *
   * @param runnable Runnable.
   */
  void execute(Runnable runnable);

  /** Shutdowns executor service. */
  void shutdown();
}