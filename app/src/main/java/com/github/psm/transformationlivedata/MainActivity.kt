package com.github.psm.transformationlivedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.psm.transformationlivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val vm: MainActivityVM by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(MainActivityVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSubmitKeyValue.setOnClickListener {
            val key = binding.key.text.toString()
            val value = binding.value.text.toString()
            if (key.isNotEmpty() && value.isNotEmpty()) {
                vm.submitKeyValue(key, value)
            }
        }

        binding.btnSubmitOnlyValue.setOnClickListener {
            val value = binding.value.text.toString()
            if (value.isNotEmpty()) {
                vm.submitOnlyValue(value)
            }
        }

        binding.btnSubmitOnlyKey.setOnClickListener {
            val key = binding.key.text.toString()
            if (key.isNotEmpty()) {
                vm.submitOnlyKey(key)
            }
        }

        binding.btnSubmitFilterMap.setOnClickListener {
            vm.submitFilterMap(binding.filterValueMap.text.toString())
        }

        vm.prefValueWithFilter.observe(this, {
            binding.outputWithMap.text = it
        })

        vm.key.observe(this, {
            binding.key.setText(it)
        })

        vm.prefValue.observe(this, {
            binding.output.text = it
            binding.value.setText(it)
        })
    }
}
