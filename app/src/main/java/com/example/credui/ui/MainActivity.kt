package com.example.credui.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.credui.Constants
import com.example.credui.databinding.ActivityCredBinding
import com.example.credui.model.AmountPickerResponse
import com.example.credui.model.BankPickerResponse
import com.example.credui.model.WidgetResponse
import com.example.credui.stackframework.model.CardUIData
import com.example.credui.utility.NetworkResult
import com.example.credui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCredBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUIInfo()
        viewModel.uiInfo.observe(this) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    initUI(it.data)
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun initUI(data: List<CardUIData>?) {
        binding.credStackView.apply {
            setStackData(data ?: listOf())
            pushCard()
            submitAction = {
                clearStack()
                visibility = View.GONE
                binding.successGroup.visibility = View.VISIBLE
                binding.txtResult.text = getResultFromResponse(it)
            }
        }

        binding.btnOkay.setOnClickListener {
            recreate()
        }
    }

    private fun getResultFromResponse(it: List<WidgetResponse>): CharSequence? {
        var amount = 0
        var bank = ""
        it.forEach {
            when (it.type) {
                Constants.AMOUNT_PICKER_WIDGET -> {
                    amount = (it as AmountPickerResponse).selectedAmount
                }

                Constants.BANK_PICKER_WIDGET -> {
                    bank = (it as BankPickerResponse).selectedBank.name
                }
            }
        }

        return "We will deposit $$amount to your $bank account shortly"
    }
}