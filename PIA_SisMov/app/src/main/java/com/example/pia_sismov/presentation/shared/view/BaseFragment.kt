package fcfm.lmad.poi.ChatPoi.presentation.shared.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.pia_sismov.presentation.shared.IBasePresenter

abstract class BaseFragment<TView, TPresenter>(
    private val ctx: Context
): Fragment()  where TPresenter: IBasePresenter<TView>//,TView: View
 {

    protected lateinit var rootView: View
    protected lateinit var presenter: TPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(getFragmentLayoutID(), container, false)
        setupPresenter(this as TView)
        return rootView
    }

    @LayoutRes
    abstract fun getFragmentLayoutID():Int
     abstract fun instantiatePresenter(): TPresenter

     private fun setupPresenter(view:TView){
         presenter = instantiatePresenter()
         presenter.attachView(view)
     }

    fun toast(context: Context = ctx, message:String, duration:Int = Toast.LENGTH_SHORT){
        Toast.makeText(context,message, duration).show()
    }

    fun showError(msgError: String) {
        toast(ctx,msgError)
    }
}