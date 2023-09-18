package hu.bme.aut.android.securityapp.ui.feature.mainmenu.shift

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.securityapp.data.repository.ShiftRepository
import hu.bme.aut.android.securityapp.data.repository.WageRepository
import javax.inject.Inject

@HiltViewModel
class ShiftScreenViewModel @Inject constructor(
    private val shiftRepository: ShiftRepository,
    private val wageRepository: WageRepository,
) : ViewModel() {

}