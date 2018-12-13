package cn.panzi.receiver.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.panzi.receiver.R
import cn.panzi.receiver.widget.CommonCard
import org.altbeacon.beacon.Beacon

//add for notification
import cn.panzi.receiver.MainActivity

class BeaconListAdapter(
    private var beaconList: List<Beacon>, private var ctxt: MainActivity
) : androidx.recyclerview.widget.RecyclerView.Adapter<BeaconListAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_beacon, parent, false)
        return MyHolder(view)
    }


    /*
    尝试在这里创建notificationmanager没成功
     */
    /*Add notification
    private fun test(){
        val notification = NotificationCompat.Builder(ctxt,"channel id test")
                .setSmallIcon(R.drawable.icon_don_s)
                //.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.icon_don))
                .setContentTitle("huge test")
                .setContentText("Test Test Test")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(300, 600, 300, 600))
                .setLights(Color.RED, 1000, 1000)
                .build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE,) as NotificationManager
        //val notificationManager = ContextCompat.getSystemService(ctxt,BeaconListAdapter) as NotificationManager
        notificationManager.notify(1, notification)
    }

    */

    override fun getItemCount(): Int {
        return beaconList.size
    }

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {
        var name = beaconList[p1].id1.toString()
        //添加提示
        if (name == "00000000-0000-0000-0000-000000000001"){
            name = "Yvonne's Beacon"
            //callback notification in MainActivity
            ctxt.notifyScan("Yvonne's Beacon","Test-1: Now Yvonne Comes",R.drawable.icon_yvonne,1)
        }
        if (name == "11111111-1111-1111-1111-111111111111"){
            name = "Peter's Beacon"
            ctxt.notifyScan("Peter's Beacon","Test-2: Here Peter Comes",R.drawable.icon_peter,2)
        }
        if (name == "00000000-0000-0000-0000-000000000002"){
            name = "ZhangYu's Beacon"
            //callback notification in MainActivity
            ctxt.notifyScan("ZhangYu's Beacon","Test-3: ZhangYu's Beacon Works",R.drawable.icon_don,3)
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
