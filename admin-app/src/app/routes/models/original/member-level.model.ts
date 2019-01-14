
export class MemberLevel {

    /**
       * 会员等级ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 等级名称
     */
    name:string;

    /**
       * 会员人数（冗余）
     */
    quantity:number;

    /**
       * 成长值满足点
     */
    growthValue:number;

    /**
       * 折扣（0.00-100.00）
     */
    discount:number;

    /**
       * 默认等级（0非默认1默认）
     */
    initial:number;

    /**
     * 默认（0非默认1默认）
     */
    defaulted:number;

  /**
       * 注册时间
     */
    createTime:string;

    /**
       * 删除（0否1是）
     */
    deleted:number;

    /**
     * 级别
     */
    rank:number;

  /**
       * 删除时间
     */
    delTime:string;
  countNumber:number;
  bonusBalance:number;
  fristRoyalty:number;
  secondRoyalty:number;
}
