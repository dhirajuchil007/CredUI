package com.example.credui.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.credui.R
import com.example.credui.databinding.BankListItemBinding
import com.example.credui.databinding.BankSelectorWidgetBinding
import com.example.credui.model.Bank
import com.example.credui.model.BankPickerResponse
import com.example.credui.model.BankPickerWidget
import com.example.credui.model.WidgetResponse
import com.example.credui.stackframework.model.Widget
import com.example.credui.stackframework.model.WidgetViewHolder

class BankSelectorViewHolder : WidgetViewHolder {
    lateinit var binding: BankSelectorWidgetBinding

    override fun initLayout(layoutInflater: LayoutInflater) {
        binding = BankSelectorWidgetBinding.inflate(layoutInflater)
    }

    override fun setView(parent: FrameLayout, data: Widget) {
        parent.addView(binding.root)

        val bankPickerWidget = data as BankPickerWidget
        val bankListAdapter = BankListAdapter(bankPickerWidget.bankList)
        binding.bankRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.bankRecyclerView.adapter = bankListAdapter
    }

    override fun getResponse(): WidgetResponse {
        return BankPickerResponse(
            type = "bank_picker",
            selectedBank = (binding.bankRecyclerView.adapter as BankListAdapter).getSelectedBank()
        )
    }
}


class BankListAdapter(private val banks: List<Bank>) :
    RecyclerView.Adapter<BankListAdapter.BankViewHolder>() {

    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val binding =
            BankListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        val bank = banks[position]
        holder.bind(bank, position == selectedPosition)
    }

    override fun getItemCount(): Int {
        return banks.size
    }

    fun getSelectedBank(): Bank {
        return banks[selectedPosition]
    }

    inner class BankViewHolder(private val binding: BankListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bank: Bank, isSelected: Boolean) {
            binding.bankName.text = bank.name
            binding.acoountNumber.text = bank.accountNumber
            Glide.with(binding.root.context).load(bank.logo).into(binding.bankLogo)
            binding.imgChecked.setImageDrawable(
                if (isSelected) {
                    binding.root.context.getDrawable(R.drawable.ic_selected)
                } else {
                    binding.root.context.getDrawable(R.drawable.ic_unselected)
                }
            )

            binding.root.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }
}
