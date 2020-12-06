package fcfm.lmad.poi.ChatPoi.presentation.shared.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.pia_sismov.presentation.shared.IBasePresenter

abstract class BaseActivity<TView, TPresenter> : AppCompatActivity()
    where TPresenter: IBasePresenter<TView>//,TView: View
{
    protected lateinit var presenter: TPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        setupPresenter(this as TView)
    }

    @LayoutRes
    abstract fun getLayout():Int
    abstract fun instantiatePresenter(): TPresenter

    private fun setupPresenter(view:TView){
        presenter = instantiatePresenter()
        presenter.attachView(view)
    }

    fun Context.toast(context:Context = applicationContext, message:String, duration:Int = Toast.LENGTH_SHORT){
        Toast.makeText(context,message, duration).show()
    }

    fun showError(errorMsg: String) {
        toast(this,errorMsg)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.detachView()
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}