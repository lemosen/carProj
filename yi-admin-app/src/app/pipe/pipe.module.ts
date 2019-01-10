import {NgModule} from '@angular/core';

// import {OrderTypePipe} from "./order-type.pipe";

@NgModule({
    declarations: [
        // OrderTypePipe,
    ],
    // exports: [OrderTypePipe,],
    imports: [],
    // providers: [OrderTypePipe]
})
export class PipeModule {
    static forRoot() {
        return {
            ngModule: PipeModule,
            providers: [],
        };
    }
}
