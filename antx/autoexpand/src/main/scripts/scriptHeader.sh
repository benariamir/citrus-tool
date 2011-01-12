#!/bin/bash

#-------------------------------------------------------------------
#    ��Ҫ�������»���������
#
#      JAVA_HOME           - JDK�İ�װ·��
#-------------------------------------------------------------------

# �ж��Ƿ���cygwin������
cygwin=false
case "`uname`" in
  CYGWIN*) cygwin=true ;;
esac

# ȷ����װ��java����������JAVA_HOME��������.
noJavaHome=false
if [ -z "$JAVA_HOME" ] ; then
    noJavaHome=true
fi
if $cygwin ; then
    [ -n "$JAVA_HOME" ] &&
        JAVA_HOME=`cygpath -u "$JAVA_HOME"`
fi
if [ ! -e "$JAVA_HOME/bin/java" ] ; then
    noJavaHome=true
fi

# ����JAVA_CMD
if $noJavaHome
then JAVA_CMD=`which java`
else JAVA_CMD="$JAVA_HOME/bin/java"
fi

noJavaCmd=false
if [ -z "$JAVA_CMD" ] ; then
    noJavaCmd=true
fi
if $cygwin ; then
    [ -n "$JAVA_CMD" ] &&
        JAVA_CMD=`cygpath -u "$JAVA_CMD"`
fi

if $noJavaCmd ; then
    echo "Error: JAVA_HOME environment variable is not set."
    exit 1
fi

# ִ��java -jar.
CMD="exec \"$JAVA_CMD\" -jar \"$0\" $@"
eval $CMD

exit 0;
