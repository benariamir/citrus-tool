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
 *
 */
package com.alibaba.antx.config.cli;

import java.text.MessageFormat;

import com.alibaba.antx.config.ConfigConstant;
import com.alibaba.antx.config.ConfigRuntime;
import com.alibaba.antx.config.ConfigRuntimeImpl;
import com.alibaba.antx.util.Profiler;
import com.alibaba.antx.util.StringUtil;
import com.alibaba.antx.util.cli.CommandLine;
import com.alibaba.citrus.logconfig.LogConfigurator;

/**
 * Antxconfig��������������
 * 
 * @author Michael Zhou
 */
public class Main {
    private static CommandLine cli;
    private static ConfigRuntime runtime;

    public static void main(String[] args) {
        // ��ʼʱ��
        Profiler.start("Starting antxconfig");

        // ��ʼ����־
        initLogging(false, null);

        // ִ��
        int returnCode = 0;

        try {
            returnCode = run(args);
        } catch (CLIException e) {
            System.err.println(e.getMessage());
            System.exit(returnCode);
        } finally {
            Profiler.release();
        }

        // ��������ʾ��ʱ��
        runtime.info("");
        runtime.info(getDuration(
                "�ܺķ�ʱ�䣺{0,choice,0#|.1#{0,number,integer}��}{1,choice,0#|.1#{1,number,integer}��}{2,number,integer}����",
                Profiler.getEntry().getDuration()));
        runtime.info("");

        // ����ֵ
        System.exit(returnCode);
    }

    private static String getDuration(String message, long duration) {
        long ms = duration % 1000;
        long secs = (duration / 1000) % 60;
        long min = duration / 1000 / 60;

        return MessageFormat.format(message, new Object[] { new Long(min), new Long(secs), new Long(ms) });
    }

    private static void initLogging(boolean verbose, String charset) {
        LogConfigurator.getConfigurator().configureDefault(verbose, charset);
    }

    private static int run(String[] args) {
        CLIManager manager = new CLIManager();

        if (args.length == 0) {
            args = new String[] { "-h" };
        }

        cli = manager.parse(args);

        String charset = cli.getOptionValue(CLIManager.OPT_CHARSET);
        ConfigRuntimeImpl runtimeImpl = new ConfigRuntimeImpl(System.in, System.out, System.err, charset);

        runtime = runtimeImpl;

        // ��ʾ����
        if (cli.hasOption(CLIManager.OPT_HELP)) {
            manager.help(runtimeImpl.getOut());
            return 0;
        }

        // ����cli�����ݣ�����runtime���ԡ�
        if (cli.hasOption(CLIManager.OPT_GUI_MODE)) {
            runtimeImpl.setGuiMode();
        }

        if (cli.hasOption(CLIManager.OPT_TEXT_MODE)) {
            runtimeImpl.setTextMode();
        }

        if (cli.hasOption(CLIManager.OPT_NON_INTERACTIVE_MODE)) {
            runtimeImpl.setInteractiveMode(ConfigConstant.INTERACTIVE_OFF);
        } else if (cli.hasOption(CLIManager.OPT_INTERACTIVE_MODE)) {
            String mode = cli.getOptionValue(CLIManager.OPT_INTERACTIVE_MODE);

            if (StringUtil.isBlank(mode)) {
                runtimeImpl.setInteractiveMode(ConfigConstant.INTERACTIVE_ON);
            } else {
                runtimeImpl.setInteractiveMode(mode);
            }
        }

        runtimeImpl.setDescriptorPatterns(cli.getOptionValue(CLIManager.OPT_INCLUDE_DESCRIPTORS), cli
                .getOptionValue(CLIManager.OPT_EXCLUDE_DESCRIPTORS));
        runtimeImpl.setPackagePatterns(cli.getOptionValue(CLIManager.OPT_INCLUDE_PACKAGES), cli
                .getOptionValue(CLIManager.OPT_EXCLUDE_PACKAGES));

        runtimeImpl.setType(cli.getOptionValue(CLIManager.OPT_TYPE));
        runtimeImpl.setDests(cli.getArgs());

        String[] outputs = null;

        if (cli.hasOption(CLIManager.OPT_OUTPUT_FILES)) {
            String outputFileNames = cli.getOptionValue(CLIManager.OPT_OUTPUT_FILES);

            if (outputFileNames != null) {
                outputs = outputFileNames.split("[,\\s]+");
            }

            runtimeImpl.setOutputs(outputs);
        }

        if (cli.hasOption(CLIManager.OPT_USER_PROPERTIES)) {
            runtimeImpl.setUserPropertiesFile(cli.getOptionValue(CLIManager.OPT_USER_PROPERTIES), charset);
        }

        if (cli.hasOption(CLIManager.OPT_SHARED_PROPERTIES) || cli.hasOption(CLIManager.OPT_SHARED_PROPERTIES_NAME)) {
            String[] sharedPropertiesFiles = StringUtil.split(cli.getOptionValue(CLIManager.OPT_SHARED_PROPERTIES));
            String sharedPropertiesName = cli.getOptionValue(CLIManager.OPT_SHARED_PROPERTIES_NAME);

            runtimeImpl.setSharedPropertiesFiles(sharedPropertiesFiles, sharedPropertiesName, charset);
        }

        if (cli.hasOption(CLIManager.OPT_VERBOSE)) {
            runtimeImpl.setVerbose();
            initLogging(true, charset);
        } else {
            initLogging(false, charset);
        }

        // ����antxconfig
        try {
            runtimeImpl.start();
        } catch (Exception e) {
            runtimeImpl.error(e);
        }

        return 0;
    }
}
