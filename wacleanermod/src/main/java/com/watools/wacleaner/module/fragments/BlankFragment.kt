package com.watools.wacleaner.module.fragments

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.adapter.RVGalleryAdapter
import com.watools.wacleaner.module.model.GalleryItem
import com.watools.wacleaner.module.utility.WACleanerConstants
import com.watools.wacleaner.module.utility.WAClenerUtility
import java.io.File
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class BlankFragment : Fragment() {

    private lateinit var rvGallery: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_blank, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View?) {
        rvGallery = view?.findViewById(R.id.rvGallery)!!

        var dataList = getDataFromStorage()
        rvGallery.layoutManager = GridLayoutManager(view.context, 3)
        rvGallery.adapter = RVGalleryAdapter(dataList)
    }

    private fun getDataFromStorage(): ArrayList<GalleryItem> {
        var dataList = ArrayList<GalleryItem>()
        var filesDirectory = File(Environment.getExternalStorageDirectory().absolutePath + WACleanerConstants.SENT_IMAGES_PATH)

        if (filesDirectory.exists()) {
            var files = filesDirectory.listFiles()
            Arrays.sort(files, object : Comparator<File> {
                override fun compare(f1: File?, f2: File?): Int {
                    val s1 = f1?.lastModified()
                    val s2 = f2?.lastModified()
                    if (s1 != null && s2 != null) {
                        if (s1 == s2) {
                            return 0
                        } else if (s1 < s2) {
                            return 1
                        } else {
                            return -1
                        }
                    }
                    return -1
                }
            })
            for (i in files.indices) {
                var item = files[i]
                dataList.add(GalleryItem(item.name, Uri.fromFile(item), WAClenerUtility.getFileSize(item.length())))
            }
        }
        return dataList
    }


}
