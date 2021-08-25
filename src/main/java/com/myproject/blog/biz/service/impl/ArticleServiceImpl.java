package com.myproject.blog.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myproject.blog.biz.entity.*;
import com.myproject.blog.biz.mapper.ArticleMapper;
import com.myproject.blog.biz.service.*;
import com.myproject.blog.common.utils.QueryPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, SysArticle> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryServie categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleTagsService articleTageService;

    @Override
    public int countByUser(SysUser user) {
        LambdaQueryWrapper<SysArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysArticle::getAuthor, user.getUsername());
        Integer count = articleMapper.selectCount(queryWrapper);
        return count;
    }

    /**
     * 按条件分页查询
     * @param sysArticle
     * @param queryPage
     * @return
     */
    @Override
    public IPage<SysArticle> list(SysArticle sysArticle, QueryPage queryPage) {
        IPage<SysArticle> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysArticle::getId);
        queryWrapper.like(StringUtils.isNotBlank(sysArticle.getTitle()), SysArticle::getTitle, sysArticle.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(sysArticle.getAuthor()), SysArticle::getAuthor, sysArticle.getAuthor());
        IPage<SysArticle> selectPage = articleMapper.selectPage(page, queryWrapper);
        findInit(selectPage.getRecords());
        return selectPage;
    }

    @Override
    public SysArticle findById(Long id) {
        SysArticle sysArticle = articleMapper.selectById(id);
        if (sysArticle != null) {
            List<SysArticle> list = new ArrayList<>();
            list.add(sysArticle);
            findInit(list);
            return sysArticle;
        }
        return null;
    }

    @Override
    public void updateAuthor(SysUser sysUser, String currentName) {
        articleMapper.updateAuthor(sysUser.getUsername(), currentName);
    }

    @Override
    @Transactional
    public void add(SysArticle sysArticle) {
        sysArticle.setCreateTime(new Date());
        articleMapper.insert(sysArticle);
        // 保存：文章-分类、文章-标签、关联数据
        this.updateArticleCategoryTags(sysArticle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id != null && id != 0) {
            articleMapper.deleteById(id);
            // 删除 文章-分类表的关联
            articleCategoryService.deleteByArticleId(id);
            // 删除 文章-标签表的关联
            articleTageService.deleteByArticleId(id);
        }
    }

    @Override
    @Transactional
    public void update(SysArticle sysArticle) {
        articleMapper.updateById(sysArticle);
        // 更新 文章-分类表、文章-标签表
        updateArticleCategoryTags(sysArticle);
    }

    @Override
    public List<SysArticle> findByTag(Long id, String userName) {
        return articleMapper.findByTag(id, userName);
    }

    @Override
    public List<SysArticle> findByCategory(Long id, String userName) {
        return articleMapper.findByCategory(id, userName);
    }

    /**
     * 更新 文章-分类、文章-标签 表的数据
     * @param sysArticle
     */
    private void updateArticleCategoryTags(SysArticle sysArticle) {
        if (sysArticle.getId() > 0) {
            if (sysArticle.getCategory() != null) { // 删除原来 文章-标签
                articleCategoryService.deleteByArticleId(sysArticle.getId());
                SysCategory sysCategory = categoryService.getById(sysArticle.getCategory());
                if (sysCategory != null) {
                    articleCategoryService.add(new SysArticleCategory(sysArticle.getId(), sysCategory.getId()));
                }
            }
            if (sysArticle.getTags() != null && sysArticle.getTags().size() > 0) {
                articleTageService.deleteByArticleId(sysArticle.getId());
                sysArticle.getTags().forEach(tag -> {
                    articleTageService.add(new SysArticleTag(sysArticle.getId(), tag.getId()));
                });
            }
        }
    }


    /**
     * 封装文章分类，标签数据
     */
    private void findInit(List<SysArticle> list) {
        if (!list.isEmpty()) {
            list.forEach(article -> {
                // 根据文章的id查询其所属的类别
                List<SysCategory> sysCategoryList = categoryService.findByArticleId(article.getId());
                if (sysCategoryList.size() > 0) {
                    // 取第一个类别赋给文章
                    article.setCategory(sysCategoryList.get(0));
                }
                List<SysTag> sysTagList = tagService.findByArticleId(article.getId());
                article.setTags(sysTagList);
            });
        }
    }
}
