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
package com.alibaba.antx.config;

import com.alibaba.antx.config.generator.VelocityTemplateEngine;

/**
 * Config�ĳ�����
 * 
 * @author Michael Zhou
 */
public interface ConfigConstant {
    /** GUI����ģʽ�� */
    String MODE_GUI = "gui";

    /** �ı�����ģʽ�� */
    String MODE_TEXT = "text";

    /** ����ģʽ���� */
    String INTERACTIVE_ON = "on";

    /** ����ģʽ���� */
    String INTERACTIVE_OFF = "off";

    /** ����ģʽ���Զ� */
    String INTERACTIVE_AUTO = "auto";

    /** Velocity���ã�������д�����parser��. */
    int VELOCITY_NUMBER_OF_PARSERS = 1;

    /** Velocity���ã�Ĭ�ϵ�macro�ļ�, ��classpath��װ�� */
    String VELOCITY_MACRO_FILE = VelocityTemplateEngine.class.getPackage().getName().replace('.', '/') + "/macro.vm";

    String UNKNWON_REFS_KEY = "_unknwonRefs";
}
