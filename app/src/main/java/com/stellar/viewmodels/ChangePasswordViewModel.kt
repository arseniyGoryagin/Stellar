    package com.stellar.viewmodels

    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.stellar.data.Repository
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.launch
    import javax.inject.Inject



    sealed interface ChangePasswordState{
        object Success : ChangePasswordState
        object Loading : ChangePasswordState
        object Error : ChangePasswordState
        object Idle : ChangePasswordState
    }



    @HiltViewModel
    class ChangePasswordViewModel @Inject constructor(private val repository: Repository) : ViewModel(){

        var changePasswordState  : ChangePasswordState by mutableStateOf(ChangePasswordState.Idle)


        fun resetState(){
            changePasswordState = ChangePasswordState.Idle
        }


        fun changePassword(newPassword : String){
            viewModelScope.launch {
                changePasswordState = ChangePasswordState.Loading
                try{
                    repository.changePassword(newPassword)
                    changePasswordState = ChangePasswordState.Success
                    println("Password changed")
                }catch (e : Exception){
                    when(e) {
                        is retrofit2.HttpException -> {
                            val repsonseBody = e.response()?.errorBody()?.string()
                            println("Error ${e.message()}\n${repsonseBody}")
                        }
                    }
                    println("Errro " + e.localizedMessage)
                    changePasswordState = ChangePasswordState.Error
                }

            }
        }

    }