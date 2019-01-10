
export class RecommendProduct {

    /**
       * 推荐位ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 推荐位（recommend表ID）
     */
    recommendId:number;

    /**
       * 报考（product表ID）
     */
    productId:number;

    /**
       * 报考编码（冗余）
     */
    productCode:string;

    /**
       * 报考名称（冗余）
     */
    productName:string;

    /**
       * 报考图片（冗余）
     */
    imgPath:string;
}
