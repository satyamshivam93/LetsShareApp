import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LetsShareAppTestModule } from '../../../test.module';
import { MyPostUpdateComponent } from 'app/entities/my-post/my-post-update.component';
import { MyPostService } from 'app/entities/my-post/my-post.service';
import { MyPost } from 'app/shared/model/my-post.model';

describe('Component Tests', () => {
  describe('MyPost Management Update Component', () => {
    let comp: MyPostUpdateComponent;
    let fixture: ComponentFixture<MyPostUpdateComponent>;
    let service: MyPostService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LetsShareAppTestModule],
        declarations: [MyPostUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MyPostUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MyPostUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MyPostService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MyPost(123);
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
        const entity = new MyPost();
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
