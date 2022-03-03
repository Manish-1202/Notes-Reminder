
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import com.example.a19012021031_practical_7.Note
import com.example.a19012021031_practical_7.NotificationUtils
import com.example.a19012021031_practical_7.note_list

class AlarmService : Service() {

    val PERSONAL_CHANNEL_ID = "PersonalChannel"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(intent!=null) {
            val id: Int? = intent.getIntExtra("id",0)

            Toast.makeText(applicationContext, id.toString(), Toast.LENGTH_SHORT).show()

            val data: Note? = note_list.getNoteItem(id!!)

            if (data != null) {
                val notificationUtils = NotificationUtils(applicationContext)
                notificationUtils.createChannel(PERSONAL_CHANNEL_ID, "PERSONAL")
                notificationUtils.sendNotificationInDefaultChannel(
                    data,
                    101
                )
            }
        }
        return START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}
