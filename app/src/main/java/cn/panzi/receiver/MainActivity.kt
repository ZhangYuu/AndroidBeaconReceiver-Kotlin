package cn.panzi.receiver

import android.app.Notification
import android.content.pm.PackageManager
import android.graphics.Region
import android.os.Bundle
import android.os.RemoteException
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import cn.panzi.receiver.adapter.BeaconListAdapter
import cn.panzi.receiver.ext.showToast
import cn.panzi.receiver.permission.RequestCallback
import cn.panzi.receiver.permission.RxPermissionRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.altbeacon.beacon.*

//add for notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity(), BeaconConsumer {

    private val PERMISSION_REQUEST_COARSE_LOCATION: Int = 1001
    private val BEACON_LAYOUT: String = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"
    private lateinit var beaconList: ArrayList<Beacon>

    private lateinit var beaconManager: BeaconManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        requestPermission()
        //test("test","hugetest")
    }

//////////////////
    open fun notifyScan(title:String,content:String,iconPath:Int,notifyId:Int){
        val notification = NotificationCompat.Builder(this,"channel id test")
                .setStyle(NotificationCompat.BigTextStyle().bigText(content))   //多行显示
                .setSmallIcon(R.drawable.icon_don_s)
                .setLargeIcon(android.graphics.BitmapFactory.decodeResource(resources,iconPath))//R.drawable.icon_don))
                //.setLargeIcon(android.graphics)
                .setContentTitle(title)
                //.setContentText(content)     //单行显示
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(300, 600, 300, 600))
                .setLights(Color.RED, 1000, 1000)
                .build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //        val notificationManager = ContextCompat.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notifyId, notification)
    }
///////////////////
    /**
     * 初始化view
     */
    private fun initView() {
        beaconList = ArrayList()
        recycle_view.setHasFixedSize(true)
        val linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recycle_view.layoutManager = linearLayoutManager
        recycle_view.adapter = BeaconListAdapter(beaconList,this)
    }

    /**
     * 初始化BeaconManager
     */
    private fun initBeaconManager() {
        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(BEACON_LAYOUT))
        beaconManager.bind(this)
    }

    override fun onBeaconServiceConnect() {
        beaconManager.addRangeNotifier { beacons, _ ->
            beacons?.let {
                beaconList.clear()
                beaconList.addAll(beacons)
                recycle_view.adapter?.notifyDataSetChanged()
            }
        }

        try {
            beaconManager.startRangingBeaconsInRegion(Region("", null, null, null))
        } catch (e: RemoteException) {
        }
    }

    /**
     * 权限请求
     */
    private fun requestPermission() {
        val requestPermission = RxPermissionRequest()
        requestPermission.request(this, object : RequestCallback {
            override fun onRequestPermissionSuccess() {
                initBeaconManager()
            }

            override fun onRequestPermissionFailure() {
                showToast(getString(R.string.no_location_permission))
            }

        }, android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    /**
     * 权限请求回调
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_COARSE_LOCATION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initBeaconManager()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        beaconManager.unbind(this)
    }
}
