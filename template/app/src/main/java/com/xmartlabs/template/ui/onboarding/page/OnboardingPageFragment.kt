package com.xmartlabs.template.ui.onboarding.page

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.xmartlabs.bigbang.ui.BaseFragment
import com.xmartlabs.template.R

@FragmentWithArgs
class OnboardingPageFragment : BaseFragment() {
  @BindView(R.id.title)
  internal lateinit var titleView: TextView
  @BindView(R.id.description)
  internal lateinit var descriptionView: TextView
  @BindView(R.id.image)
  internal lateinit var imageView: ImageView

  @Arg
  internal lateinit var onboardingPage: OnboardingPage

  @LayoutRes
  override fun getLayoutResId() = R.layout.fragment_onboarding_page

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    titleView.setText(onboardingPage.title)
    descriptionView.setText(onboardingPage.description)
  
    @Suppress("DEPRECATION")
    imageView.setImageDrawable(context.resources.getDrawable(onboardingPage.image))
  }
}
