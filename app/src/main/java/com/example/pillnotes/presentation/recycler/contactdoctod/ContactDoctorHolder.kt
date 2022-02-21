package com.example.pillnotes.presentation.recycler.contactdoctod

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.pillnotes.R
import com.example.pillnotes.databinding.ContactDoctorItemBinding
import com.example.pillnotes.domain.contactdoctor.ContactDoctorListener
import com.example.pillnotes.domain.model.ContactDoctor

class ContactDoctorHolder constructor(
    private val context: Context,
    private val itemBinding: ContactDoctorItemBinding,
    private val listener: ContactDoctorListener
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bindView(item: ContactDoctor) {
        itemBinding.apply {
            imgDelete.setOnClickListener {
                listener.onDeleteClick(item)
            }
            containerContact.setOnClickListener {
                listener.onContactDoctorClick(item)
            }
            imgDoctorCall.setOnClickListener {
                listener.onContactDoctorCallClick(item)
            }
            imgDoctorMaps.setOnClickListener {
                listener.onContactDoctorMapsClick(item)
            }
            imgPhotoDoctor.setImageBitmap(item.bitmap)
            tvDoctorName.text = item.name
            tvDoctorPhone.text = item.phone
            tvDoctorProfession.text = item.profession
            if (item.isLocation) {
                imgDoctorMaps.setImageResource(R.drawable.ic_baseline_map_24)
            } else {
                imgDoctorMaps.setImageResource(R.drawable.ic_baseline_map_none_24)
            }
        }
    }
}