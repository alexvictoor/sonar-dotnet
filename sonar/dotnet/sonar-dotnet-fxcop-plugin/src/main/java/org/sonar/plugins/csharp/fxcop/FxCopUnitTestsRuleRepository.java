/*
 * Sonar .NET Plugin :: FxCop
 * Copyright (C) 2010 Jose Chillan, Alexandre Victoor and SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.csharp.fxcop;

import org.sonar.api.platform.ServerFileSystem;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;
import org.sonar.api.rules.XMLRuleParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads the FXCop rules configuration file.
 */
public class FxCopUnitTestsRuleRepository extends RuleRepository {

  // for user extensions
  private ServerFileSystem fileSystem;
  private XMLRuleParser xmlRuleParser;

  public FxCopUnitTestsRuleRepository(ServerFileSystem fileSystem, XMLRuleParser xmlRuleParser) {
    // TODO: This class should create a FxCop rule repo for each .NET language, not only C#
    super(FxCopConstants.TEST_REPOSITORY_KEY, "cs");
    setName(FxCopConstants.TEST_REPOSITORY_NAME);
    this.fileSystem = fileSystem;
    this.xmlRuleParser = xmlRuleParser;
  }

  @Override
  public List<Rule> createRules() {
    List<Rule> rules = new ArrayList<Rule>();
    rules.addAll(xmlRuleParser.parse(FxCopUnitTestsRuleRepository.class.getResourceAsStream("/org/sonar/plugins/csharp/fxcop/rules/rules-unit-tests.xml")));
    for (File userExtensionXml : fileSystem.getExtensions(FxCopConstants.TEST_REPOSITORY_KEY, "xml")) {
      rules.addAll(xmlRuleParser.parse(userExtensionXml));
    }
    return rules;
  }
}
