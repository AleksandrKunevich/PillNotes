package com.example.pillnotes.presentation

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pillnotes.DaggerApplication
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentAlarmBinding
import com.example.pillnotes.domain.Constants
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*


class AlarmFragment : Fragment() {

    companion object {
        const val TAG = "class AlarmFragment"
    }

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    private lateinit var binding: FragmentAlarmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnCreateAlarm.setOnClickListener {

            val materialTimePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(Calendar.HOUR_OF_DAY)
                .setMinute(Calendar.MINUTE)
                .setTitleText(getString(R.string.add_alarm))
                .build()

            materialTimePicker.addOnPositiveButtonClickListener { view ->
                val calendar = Calendar.getInstance(TimeZone.getDefault())
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.set(Calendar.MINUTE, materialTimePicker.minute)
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.hour)

                val alarmManager =
                    requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager?

                val alarmClockInfo =
                    AlarmClockInfo(calendar.timeInMillis, getAlarmInfoPendingIntent())

                alarmManager!!.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent())
                binding.tvAlarmTime.text =
                    SimpleDateFormat(Constants.DATE_FORMAT_24H).format(calendar.time)
            }
            materialTimePicker.show(childFragmentManager, TAG)

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!Settings.canDrawOverlays(requireContext())) {
//                    val intent = Intent(
//                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + requireActivity().packageName)
//                    )
//                    startActivity(intent)
//                }
//            }
        }
    }

    private fun getAlarmInfoPendingIntent(): PendingIntent? {
        val alarmInfoIntent = Intent(requireContext(), AlarmFragment::class.java)
        alarmInfoIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(
            requireContext(),
            0,
            alarmInfoIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getAlarmActionPendingIntent(): PendingIntent? {
        val intent = Intent(requireContext(), AlarmActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(
            requireContext(),
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}