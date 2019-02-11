
export class OperateCategory {

    /**
       * 报考分类ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 上级报考分类ID
     */
    parent:OperateCategory;

    /**
     * 下级级报考分类ID
     */
    children:OperateCategory[];

    /**
       * 报考分类编码
     */
    categoryNo:string;

    /**
       * 报考分类名称
     */
    categoryName:string;

    /**
       * 状态（0启用1禁用）
     */
    state:number;

    /**
       * 图片路径
     */
    imgPath:string;

    /**
       * 排序
     */
    sort:number;

    /**
       * 备注
     */
    remark:string;

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
   * 广告类型
   */
  linkType:number;

  /**
   * 链接ID
   */
  linkId:number;

  /**
   * 是否显示标题
   */
  showName:number;

}
