package com.example.expenseobserver.features.updateversion.utils

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

class UpdateAppUtils(
    private val activity: Activity,
    private var description: String = "Идёт установка ExpensesObserver",
    private var title: String = "Обновление ExpensesObserver",
    private var appUrl: String = "https://www.vtb.by/app/vtb_mobile_app.apk",
    private var fileName: String = "ExpensesObserver.apk",
    private val openProgressDialog: (Long) -> Unit = {},
    private val showErrorDialog: () -> Unit = {}
) {

    private var uri: Uri? = null
    private var file: File? = null

    private var receiver: BroadcastReceiver? = null
    private var installIntent: Intent? = null

    fun installApp() {
        val externalStorageDir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        } else {
            @Suppress("DEPRECATION")
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        }
        val destination = "$externalStorageDir/$fileName"
        uri = Uri.parse("file://$destination")

        file = File(destination)
        file?.let { if (it.exists()) it.delete() }

        val request = DownloadManager.Request(Uri.parse(appUrl))
        request.let {
            it.setDescription(description)
            it.setTitle(title)
            it.setDestinationUri(uri)
        }
        val manager: DownloadManager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        registerReceiver()
        openProgressDialog.invoke(manager.enqueue(request))
    }

    private val onDownloadComplete = object : BroadcastReceiver() {
        // установка приложения после успешного скачивания файла с сайта банка
        override fun onReceive(context: Context, intent: Intent) {
            val mimeType = "application/vnd.android.package-archive"
            receiver = this
            try {
                installIntent = Intent(Intent.ACTION_VIEW)
                installIntent?.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                // для api >= 24 для доступа к файлу нужно использовать FileProvider
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    installIntent?.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                    val fileProviderURI = FileProvider.getUriForFile(
                        context,
                        context.applicationContext.packageName + ".provider",
                        file!!
                    )
                    installIntent?.setDataAndType(fileProviderURI, mimeType)
                } else {
                    // для api < 24 вместо FileProvider используем Uri.parse("file://$destination")
                    installIntent?.setDataAndType(uri, mimeType)
                }
                installFromAPK()
            } catch (e: Exception) {
                Log.e("MYNAME", e.toString())
                showErrorDialog.invoke()
            }
        }
    }

    private fun registerReceiver() {
        activity.registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    private fun installFromAPK() {
        try {
            activity.startActivity(installIntent)
            activity.unregisterReceiver(receiver)
        } catch (e: Exception) {
            Log.e("MYNAME", e.toString())
            showErrorDialog.invoke()
        }
    }
}