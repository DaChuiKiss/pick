package com.ergou.hailiao.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.ergou.hailiao.R;
import com.ergou.hailiao.mvp.ui.activity.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateService extends Service {
	
	//标题
	private int titleId = 0;
	
	private String apkUrl;
	 
	//文件存储
	private File updateDir = null;
	private File updateFile = null;
	 
	//通知栏
	private NotificationManager updateNotificationManager = null;
	private Notification updateNotification = null;
    Notification.Builder builder = null;
	//通知栏跳转Intent
	private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;	
	
		
	//下载状态
	private final static int DOWNLOAD_COMPLETE = 0;
	private final static int DOWNLOAD_FAIL = 1;	
	
	String apkName = "工学网";

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    //获取传值
	    titleId = intent.getIntExtra("titleId",0);
	    apkUrl = intent.getStringExtra("apkUrl");
	    //创建文件
	    if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
	        updateDir = new File(Environment.getExternalStorageDirectory(), Constants.downloadDir);
	        updateFile = new File(updateDir.getPath(),getResources().getString(titleId)+".apk");
	    }
	 
	    this.updateNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	    //this.updateNotification = new Notification();

        //设置下载过程中，点击通知栏，回到主界面
        updateIntent = new Intent(this, MainActivity.class);
        updateIntent.putExtra("xiazai", "xiazai");
        updatePendingIntent = PendingIntent.getActivity(this,0,updateIntent,0);

        builder = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(apkName)
                .setContentText("0%")
                .setTicker("开始下载")
                .setContentIntent(updatePendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);
        updateNotification=builder.getNotification();


        //设置通知栏显示内容
        //updateNotification.icon = R.mipmap.icon;
        //updateNotification.tickerText = "开始下载";
	    //updateNotification.setLatestEventInfo(this, apkName ,"0%",updatePendingIntent);
	    //发出通知
	    updateNotificationManager.notify(0,updateNotification);
	 
	    //开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
	    new Thread(new updateRunnable()).start();//这个是下载的重点，是下载的过程
	     
	    return super.onStartCommand(intent, flags, startId);
	}	

	
	private Handler updateHandler = new  Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        switch(msg.what){
            case DOWNLOAD_COMPLETE:
                //点击安装PendingIntent
                Uri uri = Uri.fromFile(updateFile);
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                updatePendingIntent = PendingIntent.getActivity(UpdateService.this, 0, installIntent, 0);
                 
                //android.os.Process.killProcess(android.os.Process.myPid());// 如果不加上这句的话在apk安装完成之后点击单开会崩溃  
                
                updateNotification.defaults = Notification.DEFAULT_SOUND;//铃声提醒
                //updateNotification.setLatestEventInfo(UpdateService.this, apkName , "下载完成,点击安装。", updatePendingIntent);

                builder = new Notification.Builder(UpdateService.this)
                        .setAutoCancel(true)
                        .setContentTitle(apkName)
                        .setContentText("下载完成,点击安装。")
                        .setTicker("开始下载")
                        .setContentIntent(updatePendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setWhen(System.currentTimeMillis())
                        .setOngoing(true);
                updateNotification=builder.getNotification();
                updateNotificationManager.notify(0, updateNotification);
                 
                //停止服务
                stopSelf();
                break;
            case DOWNLOAD_FAIL:
                //下载失败
                //updateNotification.setLatestEventInfo(UpdateService.this, apkName , "下载完成,点击安装。", updatePendingIntent);
                builder = new Notification.Builder(UpdateService.this)
                        .setAutoCancel(true)
                        .setContentTitle(apkName)
                        .setContentText("下载完成,点击安装。")
                        .setTicker("开始下载")
                        .setContentIntent(updatePendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setWhen(System.currentTimeMillis())
                        .setOngoing(true);
                updateNotification=builder.getNotification();
                updateNotificationManager.notify(0, updateNotification);
                break;
            default:
                stopSelf();
        }	         
	    }
	};	
	
	class updateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();
        public void run() {
            message.what = DOWNLOAD_COMPLETE;
            try{
                //增加权限;
                if(!updateDir.exists()){
                    updateDir.mkdirs();
                }
                if(!updateFile.exists()){
                    updateFile.createNewFile();
                }
                //下载函数，以QQ为例子
                //增加权限;"http://softfile.3g.qq.com:8080/msoft/179/1105/10753/MobileQQ1.0(Android)_Build0198.apk"
                long downloadSize = downloadUpdateFile(apkUrl,updateFile);
                if(downloadSize>0){
                    //下载成功
                    updateHandler.sendMessage(message);
                }
            }catch(Exception ex){
                ex.printStackTrace();
                message.what = DOWNLOAD_FAIL;
                //下载失败
                updateHandler.sendMessage(message);
            }
        }
    }	
	
	
	public long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {
        //这样的下载代码很多，我就不做过多的说明
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;
         
        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;
         
        try {
        	
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection)url.openConnection();
            httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
            if(currentSize > 0) {
                httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
            }
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            is = httpConnection.getInputStream();                  
            fos = new FileOutputStream(saveFile, false);
            byte buffer[] = new byte[4096];
            int readsize = 0;
            while((readsize = is.read(buffer)) > 0){
                fos.write(buffer, 0, readsize);
                totalSize += readsize;
                //为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
                if((downloadCount == 0)||(int) (totalSize*100/updateTotalSize)-10>downloadCount){
                    downloadCount += 10;
                    //updateNotification.setLatestEventInfo(UpdateService.this, "正在下载", (int)totalSize*100/updateTotalSize+"%", updatePendingIntent);
                    builder = new Notification.Builder(UpdateService.this)
                            .setAutoCancel(true)
                            .setContentTitle("正在下载")
                            .setContentText((int)totalSize*100/updateTotalSize+"%")
                            .setTicker("开始下载")
                            .setContentIntent(updatePendingIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setWhen(System.currentTimeMillis())
                            .setOngoing(true);
                    updateNotification=builder.getNotification();
                    updateNotificationManager.notify(0, updateNotification);
                }                       
            }
        } finally {
            if(httpConnection != null) {
                httpConnection.disconnect();
            }
            if(is != null) {
                is.close();
            }
            if(fos != null) {
                fos.close();
            }
        }
        return totalSize;
    }
	
	
	
	
}
