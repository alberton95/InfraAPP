package br.com.appinfra.appinfra.views

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.SlideFragmentBuilder
import android.Manifest
import android.content.Intent
import android.os.Bundle
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.data.SPInfo
import br.com.appinfra.appinfra.fragment.TermsConditionsSlide


class IntroActivity : MaterialIntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Método de Verificar Permissões
        verificaPermissoes()


        // Método de Transição de Slides
        backButtonTranslationWrapper
            .setEnterTranslation {
                view, percentage -> view.alpha = percentage * 5
            }


        // Slide 1 - Persmissão de GPS
        val neededPermissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
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

        // Slide 1 - Persmissão de Câmera
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


    // Verifica se as permissões estão habilitadas
    // Não - Tela de habilitar permissões
    // Sim - Joga para a tela de Loguin
    private fun verificaPermissoes(){
        if( SPInfo(this).isIntroShown()){
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
    }
}