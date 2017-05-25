package com.honglei.toolbox.utils.ok;

import android.app.Instrumentation;

/**
 * Created by hl on 2017/5/25.
 *
 *
 *
 *
 */
public class KeyboardUtil {

   /* 电话键键名 描述 键值

   public static int KEYCODE_CALL=5; //拨号键5
   public static int KEYCODE_ENDCALL = 6;//挂机键6
   public static int KEYCODE_HOME =3 ;//按键Home3
   public static int KEYCODE_MENU =82 ;//菜单键82
   public static int KEYCODE_BACK = 4;//返回键4
   public static int KEYCODE_SEARCH =84 ;//搜索键84
   public static int KEYCODE_CAMERA = 27;//拍照键27
   public static int KEYCODE_FOCUS = 80;//拍照对焦键80
   public static int KEYCODE_POWER =26 ;//电源键26
   public static int KEYCODE_NOTIFICATION =83 ;//通知键83
   public static int KEYCODE_MUTE = 91;//话筒静音键91
   public static int KEYCODE_VOLUME_MUTE =164 ;//扬声器静音键164
   public static int KEYCODE_VOLUME_UP = 24;//音量增加键24
   public static int KEYCODE_VOLUME_DOWN =25 ;//音量减小键25

    KEYCODE列表
            电话键
    键名 描述 键值

    KEYCODE_CALL 拨号键5
    KEYCODE_ENDCALL 挂机键6
    KEYCODE_HOME 按键Home3
    KEYCODE_MENU 菜单键82
    KEYCODE_BACK 返回键4
    KEYCODE_SEARCH 搜索键84
    KEYCODE_CAMERA 拍照键27
    KEYCODE_FOCUS 拍照对焦键80
    KEYCODE_POWER 电源键26
    KEYCODE_NOTIFICATION 通知键83
    KEYCODE_MUTE 话筒静音键91
    KEYCODE_VOLUME_MUTE 扬声器静音键164
    KEYCODE_VOLUME_UP 音量增加键24
    KEYCODE_VOLUME_DOWN 音量减小键25

    控制键
    键名 描述 键值

    KEYCODE_ENTER 回车键66
    KEYCODE_ESCAPE ESC键111
    KEYCODE_DPAD_CENTER 导航键 确定键23
    KEYCODE_DPAD_UP 导航键 向上19
    KEYCODE_DPAD_DOWN 导航键 向下20
    KEYCODE_DPAD_LEFT 导航键 向左21
    KEYCODE_DPAD_RIGHT 导航键 向右22
    KEYCODE_MOVE_HOME 光标移动到开始键122
    KEYCODE_MOVE_END 光标移动到末尾键123
    KEYCODE_PAGE_UP 向上翻页键92
    KEYCODE_PAGE_DOWN 向下翻页键93
    KEYCODE_DEL 退格键67
    KEYCODE_FORWARD_DEL 删除键112
    KEYCODE_INSERT 插入键124
    KEYCODE_TAB Tab键61
    KEYCODE_NUM_LOCK 小键盘锁143
    KEYCODE_CAPS_LOCK 大写锁定键115
    KEYCODE_BREAK Break/Pause键121
    KEYCODE_SCROLL_LOCK 滚动锁定键116
    KEYCODE_ZOOM_IN 放大键168
    KEYCODE_ZOOM_OUT 缩小键169

    组合键
    键名 描述

    KEYCODE_ALT_LEFT Alt+Left
    KEYCODE_ALT_RIGHT Alt+Right
    KEYCODE_CTRL_LEFT Control+Left
    KEYCODE_CTRL_RIGHT Control+Right
    KEYCODE_SHIFT_LEFT Shift+Left
    KEYCODE_SHIFT_RIGHT Shift+Right

            基本
    键名 描述 键值

    KEYCODE_0 按键'0'7
    KEYCODE_1 按键'1'8
    KEYCODE_2 按键'2'9
    KEYCODE_3 按键'3'10
    KEYCODE_4 按键'4'11
    KEYCODE_5 按键'5'12
    KEYCODE_6 按键'6'13
    KEYCODE_7 按键'7'14
    KEYCODE_8 按键'8'15
    KEYCODE_9 按键'9'16
    KEYCODE_A 按键'A'29
    KEYCODE_B 按键'B'30
    KEYCODE_C 按键'C'31
    KEYCODE_D 按键'D'32
    KEYCODE_E 按键'E'33
    KEYCODE_F 按键'F'34
    KEYCODE_G 按键'G'35
    KEYCODE_H 按键'H'36
    KEYCODE_I 按键'I'37
    KEYCODE_J 按键'J'38
    KEYCODE_K 按键'K'39
    KEYCODE_L 按键'L'40
    KEYCODE_M 按键'M'41
    KEYCODE_N 按键'N'42
    KEYCODE_O 按键'O'43
    KEYCODE_P 按键'P'44
    KEYCODE_Q 按键'Q'45
    KEYCODE_R 按键'R'46
    KEYCODE_S 按键'S'47
    KEYCODE_T 按键'T'48
    KEYCODE_U 按键'U'49
    KEYCODE_V 按键'V'50
    KEYCODE_W 按键'W'51
    KEYCODE_X 按键'X'52
    KEYCODE_Y 按键'Y'53
    KEYCODE_Z 按键'Z'54

    符号
    键名 描述

    KEYCODE_PLUS 按键'+'
    KEYCODE_MINUS 按键'-'
    KEYCODE_STAR 按键'*'
    KEYCODE_SLASH 按键'/'
    KEYCODE_EQUALS 按键'='
    KEYCODE_AT 按键'@'
    KEYCODE_POUND 按键'#'
    KEYCODE_APOSTROPHE 按键''' (单引号)
    KEYCODE_BACKSLASH 按键'\'
    KEYCODE_COMMA 按键','
    KEYCODE_PERIOD 按键'.'
    KEYCODE_LEFT_BRACKET 按键'['
    KEYCODE_RIGHT_BRACKET 按键']'
    KEYCODE_SEMICOLON 按键';'
    KEYCODE_GRAVE 按键'`'
    KEYCODE_SPACE 空格键

    小键盘
    键名 描述

    KEYCODE_NUMPAD_0 小键盘按键'0'
    KEYCODE_NUMPAD_1 小键盘按键'1'
    KEYCODE_NUMPAD_2 小键盘按键'2'
    KEYCODE_NUMPAD_3 小键盘按键'3'
    KEYCODE_NUMPAD_4 小键盘按键'4'
    KEYCODE_NUMPAD_5 小键盘按键'5'
    KEYCODE_NUMPAD_6 小键盘按键'6'
    KEYCODE_NUMPAD_7 小键盘按键'7'
    KEYCODE_NUMPAD_8 小键盘按键'8'
    KEYCODE_NUMPAD_9 小键盘按键'9'
    KEYCODE_NUMPAD_ADD 小键盘按键'+'
    KEYCODE_NUMPAD_SUBTRACT 小键盘按键'-'
    KEYCODE_NUMPAD_MULTIPLY 小键盘按键'*'
    KEYCODE_NUMPAD_DIVIDE 小键盘按键'/'
    KEYCODE_NUMPAD_EQUALS 小键盘按键'='
    KEYCODE_NUMPAD_COMMA 小键盘按键','
    KEYCODE_NUMPAD_DOT 小键盘按键'.'
    KEYCODE_NUMPAD_LEFT_PAREN 小键盘按键'('
    KEYCODE_NUMPAD_RIGHT_PAREN 小键盘按键')'
    KEYCODE_NUMPAD_ENTER 小键盘按键回车

    功能键
    键名 描述

    KEYCODE_F1 按键F1
    KEYCODE_F2 按键F2
    KEYCODE_F3 按键F3
    KEYCODE_F4 按键F4
    KEYCODE_F5 按键F5
    KEYCODE_F6 按键F6
    KEYCODE_F7 按键F7
    KEYCODE_F8 按键F8
    KEYCODE_F9 按键F9
    KEYCODE_F10 按键F10
    KEYCODE_F11 按键F11
    KEYCODE_F12 按键F12

    多媒体键
    键名 描述

    KEYCODE_MEDIA_PLAY 多媒体键 播放
    KEYCODE_MEDIA_STOP 多媒体键 停止
    KEYCODE_MEDIA_PAUSE 多媒体键 暂停
    KEYCODE_MEDIA_PLAY_PAUSE 多媒体键 播放/暂停
    KEYCODE_MEDIA_FAST_FORWARD 多媒体键 快进
    KEYCODE_MEDIA_REWIND 多媒体键 快退
    KEYCODE_MEDIA_NEXT 多媒体键 下一首
    KEYCODE_MEDIA_PREVIOUS 多媒体键 上一首
    KEYCODE_MEDIA_CLOSE 多媒体键 关闭
    KEYCODE_MEDIA_EJECT 多媒体键 弹出
    KEYCODE_MEDIA_RECORD 多媒体键 录音

            手柄按键
    键名 描述

    KEYCODE_BUTTON_1 通用游戏手柄按钮 #1
    KEYCODE_BUTTON_2 通用游戏手柄按钮 #2
    KEYCODE_BUTTON_3 通用游戏手柄按钮 #3
    KEYCODE_BUTTON_4 通用游戏手柄按钮 #4
    KEYCODE_BUTTON_5 通用游戏手柄按钮 #5
    KEYCODE_BUTTON_6 通用游戏手柄按钮 #6
    KEYCODE_BUTTON_7 通用游戏手柄按钮 #7
    KEYCODE_BUTTON_8 通用游戏手柄按钮 #8
    KEYCODE_BUTTON_9 通用游戏手柄按钮 #9
    KEYCODE_BUTTON_10 通用游戏手柄按钮 #10
    KEYCODE_BUTTON_11 通用游戏手柄按钮 #11
    KEYCODE_BUTTON_12 通用游戏手柄按钮 #12
    KEYCODE_BUTTON_13 通用游戏手柄按钮 #13
    KEYCODE_BUTTON_14 通用游戏手柄按钮 #14
    KEYCODE_BUTTON_15 通用游戏手柄按钮 #15
    KEYCODE_BUTTON_16 通用游戏手柄按钮 #16
    KEYCODE_BUTTON_A 游戏手柄按钮 A
    KEYCODE_BUTTON_B 游戏手柄按钮 B
    KEYCODE_BUTTON_C 游戏手柄按钮 C
    KEYCODE_BUTTON_X 游戏手柄按钮 X
    KEYCODE_BUTTON_Y 游戏手柄按钮 Y
    KEYCODE_BUTTON_Z 游戏手柄按钮 Z
    KEYCODE_BUTTON_L1 游戏手柄按钮 L1
    KEYCODE_BUTTON_L2 游戏手柄按钮 L2
    KEYCODE_BUTTON_R1 游戏手柄按钮 R1
    KEYCODE_BUTTON_R2 游戏手柄按钮 R2
    KEYCODE_BUTTON_MODE 游戏手柄按钮 Mode
    KEYCODE_BUTTON_SELECT 游戏手柄按钮 Select
    KEYCODE_BUTTON_START 游戏手柄按钮 Start
    KEYCODE_BUTTON_THUMBL Left Thumb Button
    KEYCODE_BUTTON_THUMBR Right Thumb Button

    待查

    键名 描述

    KEYCODE_NUM 按键Number modifier
    KEYCODE_INFO 按键Info
    KEYCODE_APP_SWITCH 按键App switch
    KEYCODE_BOOKMARK 按键Bookmark
    KEYCODE_AVR_INPUT 按键A/V Receiver input
    KEYCODE_AVR_POWER 按键A/V Receiver power
    KEYCODE_CAPTIONS 按键Toggle captions
    KEYCODE_CHANNEL_DOWN 按键Channel down
    KEYCODE_CHANNEL_UP 按键Channel up
    KEYCODE_CLEAR 按键Clear
    KEYCODE_DVR 按键DVR
    KEYCODE_ENVELOPE 按键Envelope special function
    KEYCODE_EXPLORER 按键Explorer special function
    KEYCODE_FORWARD 按键Forward
    KEYCODE_FORWARD_DEL 按键Forward Delete
    KEYCODE_FUNCTION 按键Function modifier
    KEYCODE_GUIDE 按键Guide
    KEYCODE_HEADSETHOOK 按键Headset Hook
    KEYCODE_META_LEFT 按键Left Meta modifier
    KEYCODE_META_RIGHT 按键Right Meta modifier
    KEYCODE_PICTSYMBOLS 按键Picture Symbols modifier
    KEYCODE_PROG_BLUE 按键Blue “programmable”
    KEYCODE_PROG_GREEN 按键Green “programmable”
    KEYCODE_PROG_RED 按键Red “programmable”
    KEYCODE_PROG_YELLOW 按键Yellow “programmable”
    KEYCODE_SETTINGS 按键Settings
    KEYCODE_SOFT_LEFT 按键Soft Left
    KEYCODE_SOFT_RIGHT 按键Soft Right
    KEYCODE_STB_INPUT 按键Set-top-box input
    KEYCODE_STB_POWER 按键Set-top-box power
    KEYCODE_SWITCH_CHARSET 按键Switch Charset modifier
    KEYCODE_SYM 按键Symbol modifier
    KEYCODE_SYSRQ 按键System Request / Print Screen
    KEYCODE_TV 按键TV
    KEYCODE_TV_INPUT 按键TV input
    KEYCODE_TV_POWER 按键TV power
    KEYCODE_WINDOW 按键Window
    KEYCODE_UNKNOWN 未知按键
    */

    /********
     * 模拟按键事件
     * @param KeyCode
     */
    //KeyEvent.KEYCODE_MENU
    //KeyEvent.KEYCODE_BACK
    public static void sendKeyEvent(final int KeyCode) {
        new Thread() {     //不可在主线程中调用
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }

}
