package com.example.credui.stackframework

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.credui.R
import com.example.credui.databinding.StackCardBinding
import com.example.credui.databinding.StackViewLayoutBinding
import com.example.credui.model.WidgetResponse
import com.example.credui.stackframework.model.CardUIData
import com.example.credui.stackframework.model.WidgetViewHolder
import com.example.credui.ui.viewholders.ViewHolderProvider

class CredStackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var stackHolder: LinearLayout
    private var btnAction: TextView
    private var binding: StackViewLayoutBinding

    lateinit var submitAction: (List<WidgetResponse>) -> Unit

    private val layoutInflater by lazy {
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    private var cardUiData: List<CardUIData> = listOf()

    private val viewStack: MutableList<StackCardBinding> = mutableListOf()

    private val widgetHolder: MutableList<WidgetViewHolder> = mutableListOf()

    init {
        binding = StackViewLayoutBinding.inflate(layoutInflater)
        stackHolder = binding.stackHolder
        btnAction = binding.btnAction

        btnAction.setOnClickListener {
            if (viewStack.size == cardUiData.size) {
                val resultsList = widgetHolder.map { it.getResponse() }
                submitAction(resultsList)
            } else
                pushCard()
        }
        addView(binding.root, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }


    fun setStackData(cardUiData: List<CardUIData>) {
        this.cardUiData = cardUiData
    }

    private fun popUntil(card: StackCardBinding) {
        for (i in viewStack.size - 1 downTo viewStack.indexOf(card) + 1) {
            val currCard = viewStack[i]
            val prevCard = viewStack[i - 1]
            popCard(currCard, prevCard)
        }

        setActionText()
    }

    private fun setActionText() {
        viewStack.last().apply {
            btnAction.text = cardUiData[viewStack.size - 1].mainActionText
        }
    }

    private fun popCard(currCard: StackCardBinding, prevCard: StackCardBinding) {
        stackHolder.removeView(currCard.root)
        widgetHolder.removeLast()
        viewStack.removeLast()
        prevCard.transparentOverlay.visibility = View.GONE
        prevCard.groupCollapsedElements.visibility = View.GONE
        prevCard.groupExpandedElements.visibility = View.VISIBLE
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0, 1f
        )
        prevCard.root.layoutParams = params
        setActionText()
    }

    fun pushCard() {
        val cardUIData = cardUiData[viewStack.size]
        val cardBinding = StackCardBinding.inflate(layoutInflater)

        setCardLayout(cardBinding, cardUIData)
        cardBinding.root.id = View.generateViewId()

        stackHolder.addView(cardBinding.root)

        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            0, 1f
        )
        cardBinding.root.layoutParams = params

        if (viewStack.size > 0) {
            val previousCardBinding = viewStack.last()
            previousCardBinding.transparentOverlay.visibility = VISIBLE
            val params = LayoutParams(
                LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT, 0f
            )
            params.setMargins(0, 0, 0, -1 * resources.getDimensionPixelSize(R.dimen.default_margin))

            previousCardBinding.root.layoutParams = params
            previousCardBinding.showCollapsed()
            previousCardBinding.imgArrow.setOnClickListener {
                popUntil(previousCardBinding)
            }
        }

        viewStack.add(cardBinding)
        setActionText()

    }

    private fun setCardLayout(
        cardBinding: StackCardBinding,
        cardUIData: CardUIData
    ) {
        cardBinding.apply {
            txtHeading.text = cardUIData.heading
            txtSubHeading.text = cardUIData.subHeading

        }

        cardUIData.widget?.let {
            val viewHolder: WidgetViewHolder = ViewHolderProvider.getViewHolder(it.type)
            viewHolder.initLayout(layoutInflater)
            viewHolder.setView(cardBinding.widgetHolder, it)
            widgetHolder.add(viewHolder)
        }
    }

    private fun StackCardBinding.showCollapsed() {
        groupExpandedElements.visibility = GONE
        groupCollapsedElements.visibility = VISIBLE
    }

    fun clearStack() {
        stackHolder.removeAllViews()
        viewStack.clear()
        widgetHolder.clear()
        cardUiData = listOf()
    }
}