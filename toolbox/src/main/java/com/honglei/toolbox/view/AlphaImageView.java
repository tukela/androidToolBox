/**
 * 
 */
package com.honglei.toolbox.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.mybase.app.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class AlphaImageView extends ImageView
{
	// ͼ��͸����ÿ�θı�Ĵ�С
	private int alphaDelta = 0;
	//��¼ͼƬ��ǰ��͸���ȡ�
	private int curAlpha = 0;
	//ÿ�����ٺ���͸���ȸı�һ��
	private final int SPEED = 300;
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 0x123)
			{
				//ÿ������curAlpha��ֵ
				curAlpha += alphaDelta;
				if (curAlpha > 255)
					curAlpha = 255;
				//�޸ĸ�ImageView��͸����
				AlphaImageView.this.setAlpha(curAlpha);
			}
		}
	};
	/**
	 * @param context
	 * @param attrs
	 */
	public AlphaImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
			R.styleable.AlphaImageView);
		//��ȡduration����
		int duration = typedArray.getInt(
			R.styleable.AlphaImageView_duration , 0);
		//����ͼ��͸����ÿ�θı�Ĵ�С
		alphaDelta = 255 * SPEED / duration;
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		this.setAlpha(curAlpha);
		super.onDraw(canvas);		
		final Timer timer = new Timer();
		//���̶����������Ϣ��֪ͨϵͳ�ı�ͼƬ��͸����
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{			
				Message msg = new Message();
				msg.what = 0x123;
				if (curAlpha >= 255)
				{
					timer.cancel();
				}
				else
				{
					handler.sendMessage(msg);
				}
			}
		}, 0, SPEED);	
	}
}
