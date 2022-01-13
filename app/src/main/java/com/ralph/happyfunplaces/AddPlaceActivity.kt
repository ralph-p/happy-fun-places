package com.ralph.happyfunplaces

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import com.karumi.dexter.PermissionToken

import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.PermissionRequest

import com.karumi.dexter.listener.multi.MultiplePermissionsListener





class AddPlaceActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var addToolBar: Toolbar
    lateinit var dateSelector: AppCompatEditText
    lateinit var addImageButton: TextView
    private var cal = Calendar.getInstance()
    private lateinit var dateSitListener: DatePickerDialog.OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)
        addToolBar = findViewById(R.id.toolbar_add_place)
        dateSelector = findViewById(R.id.et_date)
        addImageButton = findViewById(R.id.tv_add_image)
        setSupportActionBar(addToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        addToolBar.setNavigationOnClickListener{
            onBackPressed()
        }
        dateSitListener = DatePickerDialog.OnDateSetListener{
            view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        dateSelector.setOnClickListener(this)
        addImageButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.et_date ->{
                DatePickerDialog(
                    this@AddPlaceActivity,
                    dateSitListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
            R.id.tv_add_image ->{
                val pictureDialog = AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems = arrayOf("Select photo from Gallery", "Capture photo from camera")
                pictureDialog.setItems(pictureDialogItems) {
                    _, which ->
                        when(which) {
                            0 -> choosePhotoFromGallery()
                            1 -> Toast.makeText(this@AddPlaceActivity, "Camera", Toast.LENGTH_SHORT).show()
                        }
                }
                pictureDialog.show()
            }
        }
    }

    private fun choosePhotoFromGallery() {
        Dexter.withContext(this).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object: MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?)
            { if(report!!.areAllPermissionsGranted()){
                Toast.makeText(this@AddPlaceActivity, "Permissions granted. You may now choose a photo", Toast.LENGTH_SHORT).show()
            }}
            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest?>?,
                token: PermissionToken?
            ) {
                showRationalDialogForPermissions()
            }
        }).onSameThread().check()
    }

    private fun showRationalDialogForPermissions(){
        AlertDialog.Builder(this)
            .setMessage("You have turned off permissions required for this feature. It can be enabled under Application settings")
            .setPositiveButton("SETTINGS")
            {
                _,_ ->
                    try {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                    }
            }.setNegativeButton("Cancel") {dialog, _ -> dialog.dismiss()}
            .show()
    }

    private fun updateDateInView(){
        val dateFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        dateSelector.setText(sdf.format(cal.time).toString())
    }
}