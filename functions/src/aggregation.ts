import {firestore} from 'firebase-functions';
import {FieldValue} from 'firebase-admin/firestore';

export const updatePlaceRatings = firestore
    .document('Place/{placeId}/Review/{reviewId}')
    .onWrite(({before, after}) => {
      const placeRef = before.ref.parent.parent;
      const updateData: FirebaseFirestore.UpdateData = {};

      if (placeRef) {
        const isNew = !before.exists;
        if (isNew) {
          updateData.reviewCount = FieldValue.increment(1);
          updateData.ratings = FieldValue.increment(after.data()?.rating || 0);
        }

        const willDeleted = !after.exists;
        if (willDeleted) {
          updateData.reviewCount = FieldValue.increment(-1);
          updateData.ratings = FieldValue.increment(-before.data()?.rating || 0);
        }

        const isUpdated = before.data()?.rating !== after.data()?.rating;
        if (isUpdated) {
          updateData.ratingScore = FieldValue.increment((after.data()?.rating || 0) - (before.data()?.rating || 0));
        }

        return placeRef.update(updateData);
      }

      throw new Error('INVALID_RESOURCE: Place reference not found!');
    });
