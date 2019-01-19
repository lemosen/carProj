
export class CommodityVo {
    /**
       * 报考ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 报考编码
     */
    commodityNo:string;
    /**
       * 报考名称
     */
    commodityName:string;
    /**
       * 供应商（supplier表ID）
     */
    supplierId:number;
    /**
       * 排序
     */
    sort:number;
    /**
       * 是否参与分销(0参加1不参加)
     */
    distribution:boolean;
    /**
       * 佣金比例
     */
    commissionRate:number;
    /**
       * 运费设置（1统一运费2运费模板）
     */
    freightSet:boolean;
    /**
       * 统一运费
     */
    unifiedFreight:number;
    /**
       * 运费模板（freight_template表ID）
     */
    freightTemplate:number;
    /**
       * 库存设置（1下单减库存2支付减库存）
     */
    stockSet:boolean;
    /**
       * 体积
     */
    volume:number;
    /**
       * 重量
     */
    weight:number;
    /**
       * 是否上架（1立即上架2放入仓库）
     */
    shelf:boolean;
    /**
       * 报考介绍
     */
    description:string;
    /**
       * 创建时间
     */
    createTime:string;
    /**
       * 删除（0否1是）
     */
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;
}
