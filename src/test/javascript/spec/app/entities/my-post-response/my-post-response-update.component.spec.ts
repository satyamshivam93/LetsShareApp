import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LetsShareAppTestModule } from '../../../test.module';
import { MyPostResponseUpdateComponent } from 'app/entities/my-post-response/my-post-response-update.component';
import { MyPostResponseService } from 'app/entities/my-post-response/my-post-response.service';
import { MyPostResponse } from 'app/shared/model/my-post-response.model';

describe('Component Tests', () => {
  describe('MyPostResponse Management Update Component', () => {
    let comp: MyPostResponseUpdateComponent;
    let fixture: ComponentFixture<MyPostResponseUpdateComponent>;
    let service: MyPostResponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LetsShareAppTestModule],
        declarations: [MyPostResponseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MyPostResponseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MyPostResponseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MyPostResponseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MyPostResponse(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MyPostResponse();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
