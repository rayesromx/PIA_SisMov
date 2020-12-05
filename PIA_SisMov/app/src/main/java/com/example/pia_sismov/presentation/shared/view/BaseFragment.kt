package fcfm.lmad.poi.ChatPoi.presentation.shared.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(
    private val ctx: Context
): Fragment() {
    protected lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(getFragmentLayoutID(), container, false)
        return rootView
    }

    @LayoutRes
    abstract fun getFragmentLayoutID():Int

    fun toast(context: Context = ctx, message:String, duration:Int = Toast.LENGTH_SHORT){
        Toast.makeText(context,message, duration).show()
    }

    fun showError(msgError: String) {
        toast(ctx,msgError)
    }
}