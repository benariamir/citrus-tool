/*
 * Copyright 2010 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.antx.config.cli;

import java.io.PrintWriter;

import com.alibaba.antx.util.cli.CommandLine;
import com.alibaba.antx.util.cli.HelpFormatter;
import com.alibaba.antx.util.cli.OptionBuilder;
import com.alibaba.antx.util.cli.Options;
import com.alibaba.antx.util.cli.ParseException;
import com.alibaba.antx.util.cli.PosixParser;

/**
 * Antxconfig�����н�������
 * 
 * @author Michael Zhou
 */
public class CLIManager {
    public static final String OPT_HELP = "h";
    public static final String OPT_INCLUDE_PACKAGES = "p";
    public static final String OPT_EXCLUDE_PACKAGES = "P";
    public static final String OPT_INCLUDE_DESCRIPTORS = "d";
    public static final String OPT_EXCLUDE_DESCRIPTORS = "D";
    public static final String OPT_TEXT_MODE = "t";
    public static final String OPT_GUI_MODE = "g";
    public static final String OPT_INTERACTIVE_MODE = "i";
    public static final String OPT_NON_INTERACTIVE_MODE = "I";
    public static final String OPT_VERBOSE = "v";
    public static final String OPT_CHARSET = "c";
    public static final String OPT_USER_PROPERTIES = "u";
    public static final String OPT_SHARED_PROPERTIES = "s";
    public static final String OPT_SHARED_PROPERTIES_NAME = "n";
    public static final String OPT_OUTPUT_FILES = "o";
    public static final String OPT_TYPE = "T";
    private Options options;

    public CLIManager() {
        OptionBuilder builder = new OptionBuilder();

        options = new Options();

        options.addOption(builder.withLongOpt("help").withDescription("��ʾ������Ϣ").create(OPT_HELP));

        options.addOption(builder.withLongOpt("include-descriptors").hasArg().withDescription(
                "������Щ���������ļ������磺conf/auto-config.xml����ʹ��*��**��?ͨ��������ж���ö��ŷָ�").create(OPT_INCLUDE_DESCRIPTORS));

        options.addOption(builder.withLongOpt("exclude-descriptors").hasArg().withDescription(
                "�ų���Щ���������ļ�����ʹ��*��**��?ͨ��������ж���ö��ŷָ�").create(OPT_EXCLUDE_DESCRIPTORS));

        options.addOption(builder.withLongOpt("include-packages").hasArg().withDescription(
                "������Щ����ļ������磺target/*.war����ʹ��*��**��?ͨ��������ж���ö��ŷָ�").create(OPT_INCLUDE_PACKAGES));

        options.addOption(builder.withLongOpt("exclude-packages").hasArg().withDescription(
                "�ų���Щ����ļ�����ʹ��*��**��?ͨ��������ж���ö��ŷָ�").create(OPT_EXCLUDE_PACKAGES));

        options.addOption(builder.withLongOpt("interactive").hasOptionalArg().withDescription(
                "����ģʽ��auto|on|off��Ĭ��Ϊauto���޲�����ʾon").create(OPT_INTERACTIVE_MODE));

        options.addOption(builder.withLongOpt("non-interactive").withDescription("�ǽ���ģʽ���൱��--interactive=off").create(
                OPT_NON_INTERACTIVE_MODE));

        options.addOption(builder.withLongOpt("gui").withDescription("ͼ���û����棨����ģʽ��").create(OPT_GUI_MODE));

        options.addOption(builder.withLongOpt("text").withDescription("�ı��û����棨����ģʽ��").create(OPT_TEXT_MODE));

        options.addOption(builder.withLongOpt("verbose").withDescription("��ʾ������Ϣ").create(OPT_VERBOSE));

        options.addOption(builder.withLongOpt("charset").hasArg().withDescription("����/��������ַ���").create(OPT_CHARSET));

        options.addOption(builder.withLongOpt("userprop").hasArg().withDescription("�û������ļ�")
                .create(OPT_USER_PROPERTIES));

        options.addOption(builder.withLongOpt("shared-props").hasArg().withDescription("����������ļ�URL�б��Զ��ŷָ�").create(
                OPT_SHARED_PROPERTIES));

        options.addOption(builder.withLongOpt("shared-props-name").hasArg().withDescription("����������ļ�������").create(
                OPT_SHARED_PROPERTIES_NAME));

        options.addOption(builder.withLongOpt("output").hasArg().withDescription("����ļ�����Ŀ¼��").create(OPT_OUTPUT_FILES));

        options.addOption(builder.withLongOpt("type").hasArg().withDescription("�ļ����ͣ����磺war, jar, ear��").create(
                OPT_TYPE));
    }

    public CommandLine parse(String[] args) {
        CommandLine cli;

        try {
            cli = new PosixParser().parse(options, args);
        } catch (ParseException e) {
            throw new CLIException(e);
        }

        return cli;
    }

    public void help(PrintWriter out) {
        HelpFormatter formatter = new HelpFormatter();

        formatter.defaultSyntaxPrefix = "ʹ�÷�����";

        formatter.printHelp(out, HelpFormatter.DEFAULT_WIDTH, "antxconfig [��ѡ����] [Ŀ¼��|���ļ���]\n", "��ѡ������", options,
                HelpFormatter.DEFAULT_LEFT_PAD, HelpFormatter.DEFAULT_DESC_PAD, "\n");
    }
}
