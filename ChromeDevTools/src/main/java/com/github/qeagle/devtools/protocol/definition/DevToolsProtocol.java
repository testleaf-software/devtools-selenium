package com.github.qeagle.devtools.protocol.definition;

/*-
 * #%L
 * cdt-java-protocol-builder
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

import com.github.qeagle.devtools.protocol.definition.types.Domain;
import com.github.qeagle.devtools.protocol.definition.types.Version;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * DevTools protocol definition.
 *
 * @author TestLeaf
 */
@Getter
@Setter
public class DevToolsProtocol {
  private Version version;

  private List<Domain> domains;
}