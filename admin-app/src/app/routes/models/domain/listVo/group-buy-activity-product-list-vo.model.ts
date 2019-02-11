
export class GroupBuyActivityProductListVo {
    /**
       *
     */
    id:number;
    /**
       *
     */
    guid:string;
    /**
       * 团购活动编号
     */
    groupBuyActivityId:number;
    /**
       * 报考编号
     */
    commodityId:number;
    /**
       * 货品编号
     */
    productId:number;
    /**
       * 团购价格
     */
    groupBuyPrice:number;
    /**
       * 备货数
     */
    stockUpQuantity:number;
    /**
       * 注水数
     */
    injectWater:number;
    /**
       * 已购买数
     */
    boughtQuantity:number;
}
