package br.com.appinfra.appinfra.views

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.SlideFragmentBuilder
import android.Manifest
import android.content.Intent
import android.os.Bundle
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.models.data.SPInfo
import br.com.appinfra.appinfra.views.fragment.TermsConditionsSlide


class IntroActivity : MaterialIntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Method Check Permissions
        verificaPermissoes()

        // Method Transition Slides
        backButtonTranslationWrapper
            .setEnterTranslation {
                view, percentage -> view.alpha = percentage * 5
            }


        // Slide 1 - Check Permission Location
        val neededPermissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION)
        addSlide(
            SlideFragmentBuilder()
                .backgroundColor(R.color.slide_2)
                .buttonsColor(R.color.slide_button)
                .title( resources.getString(R.string.slide_2_title) )
                .description( resources.getString(R.string.slide_2_description) )
                .image(R.drawable.slide_2)
                .neededPermissions( neededPermissions )
                .build()
        )

        // Slide 2 - Check Permission Camera
        val possiblePermissions = arrayOf( Manifest.permission.CAMERA)
        addSlide(
            SlideFragmentBuilder()
                .backgroundColor(R.color.slide_3)
                .buttonsColor(R.color.slide_button)
                .title( resources.getString(R.string.slide_3_title) )
                .description( resources.getString(R.string.slide_3_description) )
                .image(R.drawable.slide_3)
                .possiblePermissions( possiblePermissions )
                .build()
        )

        addSlide( TermsConditionsSlide() )
    }

    // Check Permissions OK
    // Not - Activity - Enable Permissions
    // Yes - Activity Main
    private fun verificaPermissoes(){
        if( SPInfo(this).isIntroShown()){
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
    }
}