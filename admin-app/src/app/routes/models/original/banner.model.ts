
export class Banner {

    /**
       * banner表ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 标题
     */
    title:string;



    /**
       * 内容
     */
    content:string;

    /**
       * 图片路径
     */
    imgPath:string;

    /**
       * 链接路径
     */
    url:string;

    /**
       * 排序
     */
    sort:number;

    /**
       * 状态（0启用1禁用）
     */
    state:number;

    /**
       * 创建时间
     */
    createTime:string;

    /**
       * 删除（0否1是）
     */
    deleted:number;

    /**
       * 删除时间
     */
    delTime:string;

    /**
     * 轮播图类型（1报考列表，2报考详情，3爱生活文章）
     */
    bannerType:number;
}
