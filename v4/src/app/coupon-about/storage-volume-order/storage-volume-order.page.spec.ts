import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StorageVolumeOrderPage } from './storage-volume-order.page';

describe('StorageVolumeOrderPage', () => {
  let component: StorageVolumeOrderPage;
  let fixture: ComponentFixture<StorageVolumeOrderPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StorageVolumeOrderPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StorageVolumeOrderPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
