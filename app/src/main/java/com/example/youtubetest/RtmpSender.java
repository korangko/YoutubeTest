package com.example.youtubetest;

import android.content.Context;
import android.util.Log;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class RtmpSender {

    long broadcastId;

    public void broadcastStart(Context context, String streamUrl) {

        /**initial command***/
//        String[] ffmpegCommand = new String[]{"-rtsp_transport", "tcp", "-i", url, "-tune", "zerolatency","-an", "-vcodec", "libx264", "-pix_fmt",
//                "yuv420p","-threads", "6", "-c:v", "copy", "-f", "flv", "-flvflags", "no_duration_filesize", streamUrl };


        /** without dummy audio **/
        String[] ffmpegCommand = new String[]{"-rtsp_transport", "tcp", "-i", "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov",
                "-tune", "zerolatency", "-ar", "44100", "-vcodec", "libx264", "-pix_fmt",
                "yuv420p", "-threads", "6", "-c:v", "copy", "-f", "flv", "-flvflags", "no_duration_filesize", streamUrl};

        /**broadcast from phone saving **/
//        String[] ffmpegCommand = new String[]{"-i", fileSavePath,
//                "-tune", "zerolatency", "-ar", "44100", "-vcodec", "libx264", "-pix_fmt",
//                "yuv420p", "-threads", "6", "-c:v", "copy", "-f", "flv", "-flvflags", "no_duration_filesize", streamUrl};


        broadcastId = FFmpeg.executeAsync(ffmpegCommand, new ExecuteCallback() {

            @Override
            public void apply(final long executionId, final int returnCode) {
                if (returnCode == RETURN_CODE_SUCCESS) {
                    Log.i(Config.TAG, "Async command execution completed successfully.");
                } else if (returnCode == RETURN_CODE_CANCEL) {
                    Log.i(Config.TAG, "Async command execution cancelled by user.");
                } else {
                    Log.i(Config.TAG, String.format("Async command execution failed with rc=%d.", returnCode));
                }
            }
        });
    }

    public void broadcastStop(Context context) {
        FFmpeg.cancel(broadcastId);
    }
}
