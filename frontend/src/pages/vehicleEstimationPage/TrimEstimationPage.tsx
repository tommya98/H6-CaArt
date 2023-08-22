import React, { useEffect, useContext } from 'react';
import styled from 'styled-components';
import Header from '../../components/common/header/Header';
import SquareButton from '../../components/common/SquareButton';
import ToolTip from '../../components/common/ToolTip';
import CompareModal from '../../components/vehicleEstimationPage/trimEstimationPage/CompareModal';
import EBWContainer from '../../components/vehicleEstimationPage/trimEstimationPage/EBWContainer';
import EBWGuideModal from '../../components/vehicleEstimationPage/trimEstimationPage/EBWGuideModal';
import TrimCarImage from '../../components/vehicleEstimationPage/trimEstimationPage/TrimCarImage';
import TrimContainer from '../../components/vehicleEstimationPage/trimEstimationPage/TrimContainer';
import OptionExplainModal from '../../components/vehicleEstimationPage/trimEstimationPage/OptionExplainModal';
import { Link } from 'react-router-dom';
import { useModalContext } from '../../store/ModalContext';
import { useFetch } from '../../hooks/useFetch';
import { EstimationContext } from '../../util/Context';

export interface OptionType {
  optionId: number;
  optionName: string;
  description: string;
  optionImage: string;
}

interface Option {
  optionId: number;
  optionName: string;
  description: string;
  optionImage: string;
}

export interface Color {
  colorId: number;
  colorName: string;
  colorPrice: number;
  colorImage: string;
}

export interface Trim {
  trimName: string;
  description: string;
  trimImage: string;
  trimPrice: number;
  mainOptions: Option[];
  exteriorColors: Color[];
  interiorColors: Color[];
}

function TrimEstimationPage() {
  const { dispatch } = useModalContext();
  const { data } = useFetch<Trim[]>('/trims');
  const { currentEstimation, setTrim } = useContext(EstimationContext)!;

  function closeModalHandler() {
    dispatch({ type: 'CLOSE_TOOLTIP_MODAL' });
    dispatch({ type: 'CLOSE_OPTION_MODAL' });
  }

  useEffect(() => {
    if (data) {
      const trimImg = data.find(
        item => item.trimName === currentEstimation.trim.name,
      )?.trimImage as string;
      setTrim({
        name: currentEstimation.trim.name,
        price: currentEstimation.trim.price,
        img: trimImg,
      });
    }
    dispatch({ type: 'SET_TOOLTIP_TYPE', tooltipType: '엔진' });
  }, [data]);
  return (
    <>
      {<EBWGuideModal />}
      {<CompareModal data={data as Trim[]} />}
      {<ToolTip />}
      {<OptionExplainModal />}
      <Wrapper onClick={closeModalHandler}>
        <Header size="large" page={0} />
        <Layout>
          <TrimCarImage />
          <RightBox onScroll={closeModalHandler}>
            <InfoText onClick={() => dispatch({ type: 'OPEN_INFO_MODAL' })}>
              <img src="/images/question_icon.svg" />
              <span className="text-secondary-active-blue body-medium-14">
                고르기 어렵다면?
              </span>
            </InfoText>
            <EBWContainer />
            <TrimContainer data={data as Trim[]} />
            <Link to="/estimate/color">
              <SquareButton size="xm" bg="primary-blue" color="grey-1000">
                색상 선택
              </SquareButton>
            </Link>
          </RightBox>
        </Layout>
      </Wrapper>
    </>
  );
}

export default TrimEstimationPage;

const Wrapper = styled.div`
  z-index: 4;
  ::-webkit-scrollbar {
    display: none;
  }
`;

const Layout = styled.div`
  display: grid;
  height: calc(100vh - 120px);
  grid-template-columns: 2fr 1fr;
`;

const RightBox = styled.div`
  padding: 51px 0px 36px 51px;
  overflow-y: scroll;
  ::-webkit-scrollbar {
    display: none;
  }
`;

const InfoText = styled.div`
  color: var(--secondary-active-blue);
  display: inline-flex;
  gap: 4px;
  align-items: center;
  text-decoration: underline;
  text-underline-offset: 3px;
  margin-bottom: 14px;
  cursor: pointer;
`;
