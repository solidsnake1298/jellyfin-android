package org.jellyfin.mobile.player.cast

import androidx.media3.cast.CastPlayer
import androidx.media3.cast.SessionAvailabilityListener
import androidx.media3.common.Player
import com.google.android.gms.cast.framework.CastContext
import org.jellyfin.mobile.player.audio.MediaService

class CastPlayerProvider(private val mediaService: MediaService) : ICastPlayerProvider, SessionAvailabilityListener {
    private val castPlayer: CastPlayer? = try {
        CastPlayer(CastContext.getSharedInstance(mediaService)).apply {
            setSessionAvailabilityListener(this@CastPlayerProvider)
            addListener(mediaService.playerListener)
        }
    } catch (e: Exception) {
        null
    }

    override val isCastSessionAvailable: Boolean
        get() = castPlayer?.isCastSessionAvailable == true

    override fun get(): Player? = castPlayer

    override fun onCastSessionAvailable() {
        mediaService.onCastSessionAvailable()
    }

    override fun onCastSessionUnavailable() {
        mediaService.onCastSessionUnavailable()
    }
}
