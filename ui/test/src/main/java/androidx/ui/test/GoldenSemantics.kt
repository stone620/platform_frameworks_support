/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.ui.test

import androidx.ui.core.semantics.SemanticsConfiguration

// TODO(catalintudor): add remaining properties
class SemanticsPropertiesBuilder(
    var enabled: Boolean?,
    var checked: Boolean?,
    var inMutuallyExclusiveGroup: Boolean,
    var selected: Boolean
) {
    fun build(): SemanticsConfiguration {
        return createFullSemantics(
            enabled = enabled,
            checked = checked,
            inMutuallyExclusiveGroup = inMutuallyExclusiveGroup,
            selected = selected
        )
    }
}

/**
 * Ensures the created [SemanticsProperties] object doesn't have any default values set.
 * This intentionally enforces choosing every value in order to minimise possible unwanted
 * side effects. Should be used to create initial default semantics for widgets and afterwards
 * [SemanticsProperties.copyWith] should be used to create a modified copy.
 */
// TODO(catalintudor): add remaining properties
fun createFullSemantics(
    enabled: Boolean? = null,
    checked: Boolean? = null,
    selected: Boolean = false,
    inMutuallyExclusiveGroup: Boolean = false
): SemanticsConfiguration {
    return SemanticsConfiguration().also {
        it.isEnabled = enabled
        it.isChecked = checked
        it.isInMutuallyExclusiveGroup = inMutuallyExclusiveGroup
        it.isSelected = selected
    }
}

fun SemanticsConfiguration.toBuilder(): SemanticsPropertiesBuilder {
    return SemanticsPropertiesBuilder(
        enabled = isEnabled,
        checked = isChecked,
        inMutuallyExclusiveGroup = isInMutuallyExclusiveGroup,
        selected = isSelected
    )
}

/**
 * Returns a (mutated) copy of the original [SemanticsProperties] object.
 * Uses [SemanticsPropertiesBuilder] as an intermediate (mutable) representation of
 * [SemanticsProperties]
 */
fun SemanticsConfiguration.copyWith(diff: SemanticsPropertiesBuilder.() -> Unit):
        SemanticsConfiguration {
    return toBuilder()
        .apply(diff)
        .build()
}

fun SemanticsConfiguration.assertEquals(expected: SemanticsConfiguration) {
    val assertMessage = StringBuilder()

    if (isEnabled != expected.isEnabled) {
        assertMessage.append("\n- expected 'isEnabled' = ${expected.isEnabled} but was $isEnabled")
    }

    if (isChecked != expected.isChecked) {
        assertMessage.append("\n- expected 'isChecked' = ${expected.isChecked} but was $isChecked")
    }

    if (isInMutuallyExclusiveGroup != expected.isInMutuallyExclusiveGroup) {
        assertMessage.append(
            "\n- expected 'inMutuallyExclusiveGroup' = ${expected.isInMutuallyExclusiveGroup} " +
                    "but was $isInMutuallyExclusiveGroup"
        )
    }
    if (isSelected != expected.isSelected) {
        assertMessage.append(
            "\n- expected 'isSelected' = ${expected.isSelected} but was $isSelected"
        )
    }

    if (assertMessage.isNotEmpty()) {
        throw AssertionError(
            "Expected semantics is not equal to the current one: " +
                    assertMessage.toString()
        )
    }
}