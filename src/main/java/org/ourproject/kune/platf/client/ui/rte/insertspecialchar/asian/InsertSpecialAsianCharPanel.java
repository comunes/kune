package org.ourproject.kune.platf.client.ui.rte.insertspecialchar.asian;

import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.occidental.AbstractInsertCharPanel;

import cc.kune.core.shared.i18n.I18nTranslationService;

public class InsertSpecialAsianCharPanel extends AbstractInsertCharPanel implements InsertSpecialAsianCharView {

    private static char[] SPECIAL_CHARS = { '・', '×', '【', '】', '『', '』', '《', '》', '“', '”', '「', '」', '〈', '〉', '≪',
            '≫', '｢', '｣', '＜', '＞', '‘', '’', '〔', '〕', '㈱', '㏍', '～', '㌍', '，', '〃', '仝', 'ゝ', 'ゞ', '々', '〒', '※',
            '″', '㌔', '㎏', '㎞', '≒', '㌘', '★', '●', '㊦', '※', '▲', '▽', '△', '▼', '⊿', '◇', '◆', '□', '■', '↓', '〆',
            '♯', '／', '＼', '㍼', '㊤', '☆', '○', '§', '㎝', '㌢', '㎝', '￠', '㌣', '㍽', '㈹', '†', '゛', '∥', '｜', '′', '″',
            '℃', '￥', 'Å', '￠', '￡', '％', '‰', '＄', '°', '㊥', '℡', '°', '℃', '㌧', '∵', '‥', '№', '◎', '％', '㌫', '×',
            '゜', '㊧', '㍻', '㎡', '㎡', '㌶', '㌻', '㌻', '※', '．', '｡', '。', '㊨', '㎜', '㍉', '㎎', '㍊', '㎜', '∞', '㍍', '㍾',
            '㈲', '∴', '～', '㍑', '㍗', '㈀', '㈁', '㈂', '㈃', '㈄', '㈅', '㈆', '㈇', '㈈', '㈎', '㈏', '㈐', '㈑', '㈒', '㈓' };

    public InsertSpecialAsianCharPanel(InsertSpecialCharDialog insertSpecialCharDialog, I18nTranslationService i18n) {
        super(insertSpecialCharDialog, i18n.t("Asian characters"), i18n.t(InsertSpecialCharDialog.DEF_LABEL),
                SPECIAL_CHARS, 7, 20);
        insertSpecialCharDialog.addTab(this);
    }
}