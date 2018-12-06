package cn.panzi.receiver.adapter

import android.app.NotificationChannel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.panzi.receiver.R
import cn.panzi.receiver.widget.CommonCard
import org.altbeacon.beacon.Beacon

//add for notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

//import android.support.v7.app.AppCompatActivity
//import android.support.v4.app.NotificationCompat



class BeaconListAdapter(
    private var beaconList: List<Beacon>
) : androidx.recyclerview.widget.RecyclerView.Adapter<BeaconListAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_beacon, parent, false)
        return MyHolder(view)
    }

    /*Add notification*/

    /*
    private fun test(){
        val notification = NotificationCompat.Builder(this,"channel id test")
                .setSmallIcon(R.drawable.icon_don_s)
                //.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.icon_don))
                .setContentTitle("huge test")
                .setContentText("Test Test Test")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(300, 600, 300, 600))
                .setLights(Color.RED, 1000, 1000)
                .build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //        val notificationManager = ContextCompat.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
    */

    /*Add notification End*/

    override fun getItemCount(): Int {
        return beaconList.size
    }

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {
        var name = beaconList[p1].id1.toString()
        if (name == "00000000-0000-0000-0000-000000000000"){
            name = "test001"
        }
        if (name == "00000000-0000-0000-0000-000000000001"){
            name = "test002"
        }
        val distance = beaconList[p1].distance.toString()
        holder.commonCard.setCardTitleText(name)
        holder.commonCard.setCardSubscribeText(distance)
        holder.commonCard.setCardImageRes(R.mipmap.ic_launcher)
    }

    class MyHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val commonCard: CommonCard = itemView.findViewById(R.id.common_card)
    }

}