package com.example.joern.playground

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import com.robertlevonyan.components.picker.ItemModel
import com.robertlevonyan.components.picker.PickerDialog
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy

class MainActivity : AppCompatActivity() {

    private lateinit var pickerDialog: PickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val matisse = findViewById<Button>(R.id.start_matisse)
        matisse.setOnClickListener {
            Matisse.from(this@MainActivity)
                    .choose(MimeType.ofAll())
                    .countable(true)
                    .maxSelectable(9)
                    .capture(true)
                    .captureStrategy(
                            CaptureStrategy(false, "com.example.joern.playground.fileprovider",
                                    "test"))
                    .gridExpectedSize(320)
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(Glide4Engine())
                    .forResult(42)
        }

        val imagePicker = findViewById<Button>(R.id.start_imagepicker)
        imagePicker.setOnClickListener {
            ImagePicker.create(this)
                    .theme(R.style.ImagePickerTheme)
                    .multi()
                    .showCamera(true)
                    .includeVideo(true)
                    .folderMode(false)
                    .imageDirectory("COYO")
//                    .toolbarArrowColor(Color.parseColor("#EEEEEE"))
                    .limit(10)
                    .start(REQUEST_CODE_IMAGEPICKER)
        }

        val button = findViewById<Button>(R.id.upload_attachment)
        button.setOnClickListener {
            val items = ArrayList(listOf(
                    ItemModel(ItemModel.ITEM_CAMERA, "Take photo",
                            itemBackgroundColor = Color.GREEN),
                    ItemModel(ItemModel.ITEM_GALLERY, "Gallery"),
                    ItemModel(ItemModel.ITEM_VIDEO_GALLERY, "Videos"),
                    ItemModel(ItemModel.ITEM_FILES, "Files")
            ))

            pickerDialog = PickerDialog.Builder(this) // Activity or Fragment
                    .setTitle("Choose wisely")
                    .setTitleTextSize(17f)
                    .setTitleTextColor(Color.BLACK)
                    .setListType(PickerDialog.TYPE_GRID)
                    .setItems(items)
                    .setDialogStyle(PickerDialog.DIALOG_MATERIAL)
                    .create()
            pickerDialog.show()
        }

        val document = findViewById<Button>(R.id.choose_document)
        document.setOnClickListener {
            startActivityForResult(Intent().apply {
                action = Intent.ACTION_GET_CONTENT
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/*"
                // TODO More types!!
//                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf", "application/msword"))
            }, REQUEST_CODE_DOCUMENT)
        }
        val gallery = findViewById<Button>(R.id.choose_gallery)
        gallery.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_OPEN_DOCUMENT
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }

            startActivityForResult(Intent.createChooser(intent, "Choose app for gallery"),
                    REQUEST_CODE_GALLERY)
        }
        val takePhoto = findViewById<Button>(R.id.choose_take_photo)
        val audio = findViewById<Button>(R.id.choose_audio)
        val location = findViewById<Button>(R.id.choose_location)
        val contact = findViewById<Button>(R.id.choose_contact)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, requestCode.toString())
        Log.d(TAG, resultCode.toString())
        Log.d(TAG, data?.data.toString())

        if (Intent.ACTION_SEND_MULTIPLE == data?.action && data.hasExtra(Intent.EXTRA_STREAM)) {
            // retrieve a collection of selected images
            val list: ArrayList<Parcelable> =
                    intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            // iterate over these images
            for (item in list) {
                val uri = item as Uri
                Log.d(TAG, "Uri: $uri")
            }
        }

        if (requestCode == REQUEST_CODE_IMAGEPICKER && resultCode == RESULT_OK) {
            data?.let {
                val images: List<Image> = it.getParcelableArrayListExtra("selectedImages")
                for (image in images) {
                    Log.d(TAG, image.id.toString())
                    Log.d(TAG, image.name)
                    Log.d(TAG, image.path)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        pickerDialog.onPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        const val TAG = "MainActivity"
        const val REQUEST_CODE_DOCUMENT = 1
        const val REQUEST_CODE_GALLERY = 2
        const val REQUEST_CODE_IMAGEPICKER = 3
    }
}
