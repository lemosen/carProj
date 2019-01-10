import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeptTreeComponent} from './dept-tree.component';

describe('DeptTreeComponent', () => {
    let component: DeptTreeComponent;
    let fixture: ComponentFixture<DeptTreeComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DeptTreeComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DeptTreeComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
