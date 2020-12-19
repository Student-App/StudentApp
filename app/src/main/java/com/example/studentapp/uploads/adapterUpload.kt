package com.example.studentapp.uploads

import android.content.ClipData
import com.example.studentapp.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.pdf_file_row.view.*

class adapterUpload(val pdfFile: Model_upload): Item<ViewHolder>(){



    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.filename_pdf.text = pdfFile.file_name

    }

    override fun getLayout(): Int {
        return R.layout.pdf_file_row
    }


}