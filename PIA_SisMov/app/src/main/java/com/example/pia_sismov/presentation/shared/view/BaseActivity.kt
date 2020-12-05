package fcfm.lmad.poi.ChatPoi.presentation.shared.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    @LayoutRes
    abstract fun getLayout():Int

    fun Context.toast(context:Context = applicationContext, message:String, duration:Int = Toast.LENGTH_SHORT){
        Toast.makeText(context,message, duration).show()
    }

    fun showError(errorMsg: String) {
        toast(this,errorMsg)
    }

    override fun onPause() {
        super.onPause()
    }


}