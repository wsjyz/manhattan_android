package com.ivan.android.manhattanenglish.app.remote.info;

import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-4
 * Time: PM4:29
 */
public class InfomationServiceImpl extends AbstractService implements InfomationService {
    @Override
    public OpenPage<Infomation> loadLatestInfomation(OpenPage<Infomation> page) {
        if(page == null) page = new OpenPage<Infomation>();
        page.setTotal(40);
        int from = page.getPageSize() * (page.getPageNo() - 1);
        int to = Math.min((int) page.getTotal(), page.getPageNo() * page.getPageSize());
        page.setRows(mock(from, to));
        return page;
    }

    private List<Infomation> mock(int from, int to) {
        List<Infomation> result = new ArrayList<Infomation>();
        for (int i = from; i < to; i++) {
            Infomation info = new Infomation();
            info.setId("id:" + i);
            info.setTitle("《新概念英语第一册（中册）》");
            info.setContent("《新概念英语》（New Concept English）作为享誉全球的最为经典地道的英语教材，以其严密的体系性、严谨的科学性、精湛的实用性、浓郁的趣味性深受英语学习者的青睐，新概念英语一册(英语初阶)基础班---- 学习英语的敲门砖（First Things First）讲练基本语音、语调（包括所有的音标、连读、同化）及英语中的基本语法、词法、句法及句型结构知识。学好第一册，是练好英语基本功的关键，适合于英语基础差，欲在短期内掌握英语基础的学习者。掌握后，可以参加中考一类考试。tingroom不仅为网友提供新概念英语第一册在线收听，还汇总了自学导读、课堂笔记、语法以及单词学习。 ");
            info.setCreateTime(new Date());
            result.add(info);
        }
        return result;
    }
}
